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
    private static final String CREATE_QUERY =
            "INSERT INTO review_like (review_id, user_id, positive) VALUES (?, ?, ?)";
    private static final String DELETE_QUERY =
            "DELETE FROM review_like WHERE review_id = ? AND user_id = ?";
    private static final String UPDATE_QUERY =
            "UPDATE review_like SET positive = ? WHERE review_id = ? AND user_id = ?";
    private static final String FIND_BY_REVIEW_ID_AND_USER_ID_QUERY =
            "SELECT * FROM review_like WHERE review_id = ? AND user_id = ?";

    public ReviewLikeDbStorage(JdbcTemplate jdbcTemplate,
                               RowMapper<ReviewLike> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public void createReviewLike(long reviewId, long userId, boolean isPositive) {
        insert(CREATE_QUERY, reviewId, userId, isPositive);
    }

    @Override
    public void deleteReviewLike(long reviewId, long userId) {
        delete(DELETE_QUERY, reviewId, userId);
    }

    @Override
    public int isReviewLiked(long reviewId, long userId) {
        Optional<ReviewLike> like = findOne(FIND_BY_REVIEW_ID_AND_USER_ID_QUERY, reviewId, userId);
        if (like.isEmpty()) {
            return 0;
        } else if (like.get().isPositive()) {
            return 1;
        }
        return -1;
    }

    @Override
    public void updateReviewLike(long reviewId, long userId, boolean isPositive) {
        update(UPDATE_QUERY, isPositive, reviewId, userId);
    }
}
