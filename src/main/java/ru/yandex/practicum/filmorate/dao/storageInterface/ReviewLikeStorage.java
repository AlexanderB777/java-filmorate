package ru.yandex.practicum.filmorate.dao.storageInterface;

public interface ReviewLikeStorage {
    void createReviewLike(long reviewId, long userId);

    void deleteReviewLike(long reviewId, long userId);

    int getCountByReviewId(long reviewId);

    boolean isReviewLiked(long reviewId, long userId);
}
