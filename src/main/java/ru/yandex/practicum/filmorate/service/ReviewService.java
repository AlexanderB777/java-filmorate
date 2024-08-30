package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.mappers.ReviewMapper;
import ru.yandex.practicum.filmorate.dao.storageInterface.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ReviewNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.FeedEvent;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewStorage reviewStorage;
    private final ReviewMapper reviewMapper;
    private final ReviewLikeStorage reviewLikeStorage;
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final FeedEventStorage feedEventStorage;

    @Transactional
    public ReviewDto create(ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        if (filmStorage.findById(review.getFilmId()).isEmpty()) {
            throw new FilmNotFoundException(review.getFilmId());
        }
        if (userStorage.findById(review.getUserId()).isEmpty()) {
            throw new UserNotFoundException(review.getUserId());
        }
        review = reviewStorage.save(review);
        feedEventStorage.addFeedEvent(
                new FeedEvent(review.getUserId(),
                        review.getReviewId(),
                        EventType.REVIEW,
                        Operation.ADD)
        );
        return reviewMapper.toDto(review);
    }

    @Transactional
    public ReviewDto update(ReviewDto reviewDto) {
        if (reviewDto.getReviewId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Для обновления объект должен содержать id");
        }
        long id = reviewDto.getReviewId();

        Review storedReview = reviewStorage
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        storedReview.setContent(reviewDto.getContent());
        storedReview.setIsPositive(reviewDto.getIsPositive());
        feedEventStorage.addFeedEvent(
                new FeedEvent(storedReview.getUserId(),
                        id,
                        EventType.REVIEW,
                        Operation.UPDATE)
        );
        return reviewMapper.toDto(reviewStorage.update(storedReview));
    }

    public void delete(long id) {
        Review review = reviewStorage.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        feedEventStorage.addFeedEvent(
                new FeedEvent(review.getUserId(),
                        review.getReviewId(),
                        EventType.REVIEW,
                        Operation.REMOVE)
        );
        reviewStorage.delete(id);
    }

    public ReviewDto findById(long id) {
        Review storedReview = reviewStorage
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        return reviewMapper.toDto(storedReview);
    }

    public List<ReviewDto> getReviewsByFilmId(long filmId, int count) {
        if (filmId == 0 && count == 0) {
            List<Review> reviews = reviewStorage.getAll();
            reviews.sort(Comparator.comparing(Review::getUseful));
            return reviewMapper.toDto(reviews.reversed());
        }
        if (filmId == 0 && count > 0) {
            return reviewMapper.toDto(reviewStorage.getAllWithLimit(count));
        }
        List<Review> reviews;
        if (count > 0) {
            reviews = reviewStorage.getByFilmIdWithLimit(filmId, count);
        } else {
            reviews = reviewStorage.getByFilmId(filmId);
        }
        reviews.sort(Comparator.comparing(Review::getUseful).reversed().thenComparing(Review::getReviewId));
        return reviewMapper.toDto(reviews);
    }

    public ReviewDto putLike(long reviewId, long userId, boolean isPositive) {

        switch (reviewLikeStorage.isReviewLiked(reviewId, userId)) {
            case (0) -> {
                reviewLikeStorage.createReviewLike(reviewId, userId, isPositive);
                if (isPositive) {
                    reviewStorage.increaseUseful(reviewId);
                } else {
                    reviewStorage.decreaseUseful(reviewId);
                }
            }

            case (-1) -> {
                if (isPositive) {
                    reviewLikeStorage.updateReviewLike(reviewId, userId, true);
                    reviewStorage.increaseUseful(reviewId);
                    reviewStorage.increaseUseful(reviewId);
                } else {
                    return null;
                }
            }

            case (1) -> {
                if (isPositive) {
                    return null;
                } else {
                    reviewLikeStorage.updateReviewLike(reviewId, userId, false);
                    reviewStorage.decreaseUseful(reviewId);
                    reviewStorage.decreaseUseful(reviewId);
                }
            }
        }
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }

    public ReviewDto deleteLike(long reviewId, long userId, boolean isPositive) {
        int liked = reviewLikeStorage.isReviewLiked(reviewId, userId);

        switch (liked) {
            case (0) -> {
                return null;
            }

            case (-1) -> {
                if (isPositive) {
                    return null;
                } else {
                    reviewLikeStorage.deleteReviewLike(reviewId, userId);
                    reviewStorage.increaseUseful(reviewId);
                }
            }

            case (1) -> {
                if (isPositive) {
                    reviewLikeStorage.deleteReviewLike(reviewId, userId);
                    reviewStorage.decreaseUseful(reviewId);
                } else {
                    return null;
                }
            }

        }
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }
}