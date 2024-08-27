package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.ReviewLikeStorage;
import ru.yandex.practicum.filmorate.model.ReviewLike;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ReviewLikeDbStorage extends BaseDbStorage<ReviewLike> implements ReviewLikeStorage {
    private static final String CREATE_lIKE_QUERY = "INSERT INTO review_like (review_id, user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM review_like WHERE review_id = ? AND user_id = ?";
    private static final String LIKES_BY_REVIEW_ID_QUERY = "SELECT * FROM review_like WHERE review_id = ?";
    private static final String FIND_BY_REVIEW_ID_AND_USER_ID_QUERY =
            "SELECT * FROM review_like WHERE review_id = ? AND user_id = ?";

    public ReviewLikeDbStorage(JdbcTemplate jdbcTemplate, RowMapper<ReviewLike> rowMapper, RowMapper<ReviewLike> reviewLikeRowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public void createReviewLike(long reviewId, long userId) {
        insert(CREATE_lIKE_QUERY, reviewId, userId);
    }

    @Override
    public void deleteReviewLike(long reviewId, long userId) {
        delete(DELETE_LIKE_QUERY, reviewId, userId);
    }

    @Override
    public int getCountByReviewId(long reviewId) {
        List<ReviewLike> likes = findMany(LIKES_BY_REVIEW_ID_QUERY, reviewId);
        return likes.size();
    }

    @Override
    public boolean isReviewLiked(long reviewId, long userId) {
        Optional<ReviewLike> like = findOne(FIND_BY_REVIEW_ID_AND_USER_ID_QUERY, reviewId, userId);
        return like.isPresent();
    }
}
