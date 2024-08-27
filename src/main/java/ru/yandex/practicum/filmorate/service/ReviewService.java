package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.mappers.ReviewMapper;
import ru.yandex.practicum.filmorate.dao.storageInterface.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ReviewNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewStorage reviewStorage;
    private final ReviewMapper reviewMapper;
    private final ReviewLikeStorage reviewLikeStorage;
    private final ReviewDislikeStorage reviewDislikeStorage;
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    public ReviewDto create(ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        if (filmStorage.findById(review.getFilmId()).isEmpty()) {
            throw new FilmNotFoundException(review.getFilmId());
        } else if (userStorage.findById(review.getUserId()).isEmpty()) {
            throw new UserNotFoundException(review.getUserId());
        } else {
            return reviewMapper.toDto(reviewStorage.save(review));
        }
    }

    public ReviewDto update(ReviewDto reviewDto) {
        long id;
        if (reviewDto.getReviewId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Для обновления объект должен содержать id");
        } else {
            id = reviewDto.getReviewId();
        }
        Review storedReview = reviewStorage
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        storedReview.setContent(reviewDto.getContent());
        storedReview.setIsPositive(reviewDto.getIsPositive());
        storedReview.setUserId(reviewDto.getUserId());
        storedReview.setFilmId(reviewDto.getFilmId());
        storedReview.setUseful(reviewDto.getUseful());
        return reviewMapper.toDto(reviewStorage.update(storedReview));
    }

    public void delete(long id) {
        reviewStorage.delete(id);
    }

    public ReviewDto findById(long id) {
        Review storedReview = reviewStorage
                .findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        return reviewMapper.toDto(storedReview);
    }

    public List<ReviewDto> getReviewsByFilmId(long filmId, int count) {
        if (count > 0) {
            return reviewMapper.toDto(reviewStorage.getByFilmIdWithLimit(filmId, count));
        } else {
            return reviewMapper.toDto(reviewStorage.getByFilmId(filmId));
        }
    }

    public ReviewDto putLike(long reviewId, long userId) {
        if (reviewLikeStorage.isReviewLiked(reviewId, userId)) {
            return null;
        }
        if (reviewDislikeStorage.isReviewDisliked(reviewId, userId)) {
            reviewDislikeStorage.deleteReviewDislike(reviewId, userId);
            reviewStorage.increaseUseful(reviewId);
        }
        reviewLikeStorage.createReviewLike(reviewId, userId);
        reviewStorage.increaseUseful(reviewId);
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }

    public ReviewDto putDislike(long reviewId, long userId) {
        if (reviewDislikeStorage.isReviewDisliked(reviewId, userId)) {
            return null;
        }
        if (reviewLikeStorage.isReviewLiked(reviewId, userId)) {
            reviewLikeStorage.deleteReviewLike(reviewId, userId);
            reviewStorage.decreaseUseful(reviewId);
        }
        reviewStorage.decreaseUseful(reviewId);
        reviewDislikeStorage.createReviewDislike(reviewId, userId);
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }

    public ReviewDto deleteDislike(long reviewId, long userId) {
        if (reviewDislikeStorage.isReviewDisliked(reviewId, userId)) {
            reviewDislikeStorage.deleteReviewDislike(reviewId, userId);
            reviewStorage.increaseUseful(reviewId);
        }
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }

    public ReviewDto deleteLike(long reviewId, long userId) {
        if (reviewLikeStorage.isReviewLiked(reviewId, userId)) {
            reviewLikeStorage.deleteReviewLike(reviewId, userId);
            reviewStorage.decreaseUseful(reviewId);
        }
        return reviewMapper
                .toDto(reviewStorage
                        .findById(reviewId)
                        .orElseThrow(() -> new ReviewNotFoundException(reviewId)));
    }
}