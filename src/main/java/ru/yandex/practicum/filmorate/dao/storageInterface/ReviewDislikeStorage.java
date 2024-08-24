package ru.yandex.practicum.filmorate.dao.storageInterface;

public interface ReviewDislikeStorage {
    void createReviewDislike(long reviewId, long userId);

    void deleteReviewDislike(long reviewId, long userId);

    int getCountByReviewId(long reviewId);

    boolean isReviewDisliked(long reviewId, long userId);
}
