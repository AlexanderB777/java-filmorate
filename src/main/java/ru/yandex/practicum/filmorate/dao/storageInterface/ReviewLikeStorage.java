package ru.yandex.practicum.filmorate.dao.storageInterface;

public interface ReviewLikeStorage {
    void createReviewLike(long reviewId, long userId, boolean isPositive);

    void deleteReviewLike(long reviewId, long userId);

    int isReviewLiked(long reviewId, long userId);

    void updateReviewLike(long reviewId, long userId, boolean isPositive);
}
