package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.ReviewDislikeStorage;
import ru.yandex.practicum.filmorate.model.ReviewDislike;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDislikeDbStorage extends BaseDbStorage<ReviewDislike> implements ReviewDislikeStorage {
    private static final String CREATE_DISLIKE_QUERY = "INSERT INTO review_dislike (review_id, user_id) VALUES (?, ?)";
    private static final String DELETE_DISLIKE_QUERY = "DELETE FROM review_dislike WHERE review_id = ? AND user_id = ?";
    private static final String DISLIKES_BY_REVIEW_ID_QUERY = "SELECT * FROM review_dislike WHERE review_id = ?";
    private static final String FIND_BY_REVIEW_ID_AND_USER_ID_QUERY =
            "SELECT * FROM review_dislike WHERE review_id = ? AND user_id = ?";

    public ReviewDislikeDbStorage(JdbcTemplate jdbcTemplate, RowMapper<ReviewDislike> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public void createReviewDislike(long reviewId, long userId) {
        insert(CREATE_DISLIKE_QUERY, reviewId, userId);
    }

    @Override
    public void deleteReviewDislike(long reviewId, long userId) {
        delete(DELETE_DISLIKE_QUERY, reviewId, userId);
    }

    @Override
    public int getCountByReviewId(long reviewId) {
        List<ReviewDislike> dislikes = findMany(DISLIKES_BY_REVIEW_ID_QUERY, reviewId);
        return dislikes.size();
    }

    @Override
    public boolean isReviewDisliked(long reviewId, long userId) {
        Optional<ReviewDislike> dislike = findOne(FIND_BY_REVIEW_ID_AND_USER_ID_QUERY, reviewId, userId);
        return dislike.isPresent();
    }
}
