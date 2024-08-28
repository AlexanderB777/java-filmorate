package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.ReviewStorage;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ReviewDbStorage extends BaseDbStorage implements ReviewStorage {
    private static final String INSERT_QUERY =
            "INSERT INTO reviews (content, positive, user_id, film_id, useful) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE reviews SET content = ?, positive = ?, user_id = ?, film_id = ?, useful = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM reviews WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM reviews WHERE id = ?";
    private final static String REVIEWS_BY_FILM_ID_QUERY = "SELECT * FROM reviews WHERE film_id = ?";
    private static final String REVIEWS_BY_FILM_ID_WITH_LIMIT_QUERY =
            "SELECT * FROM reviews WHERE film_id = ? LIMIT ?";
    private static final String INCREASE_USEFUL_QUERY = " UPDATE reviews SET useful = useful + 1 WHERE id = ?";
    private static final String DECREASE_USEFUL_QUERY = " UPDATE reviews SET useful = useful - 1 WHERE id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM reviews";
    private static final String GET_ALL_WITH_LIMIT_QUERY = "SELECT * FROM reviews LIMIT ?";

    public ReviewDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Review> reviewRowMapper) {
        super(jdbcTemplate, reviewRowMapper);
    }

    @Override
    public Review save(Review review) {
        long id = insert(INSERT_QUERY,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful());
        review.setReviewId(id);
        log.info("Saving review: {}", review);
        return review;
    }

    public Review update(Review review) {
        log.info("Updating review: {}", review);
        long id = review.getReviewId();
        update(UPDATE_QUERY,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful(),
                id);
        return review;
    }

    @Override
    public Optional<Review> findById(long id) {
        log.info("Finding review by id: {}", id);
        Optional<Review> optionalReview = findOne(FIND_BY_ID_QUERY, id);
        return optionalReview;
    }

    @Override
    public void delete(long id) {
        log.info("Deleting review by id: {}", id);
        delete(DELETE_QUERY, id);
    }

    @Override
    public List<Review> getByFilmIdWithLimit(long filmId, int limit) {
        return findMany(REVIEWS_BY_FILM_ID_WITH_LIMIT_QUERY, filmId, limit);
    }

    @Override
    public List<Review> getByFilmId(long filmId) {
        return findMany(REVIEWS_BY_FILM_ID_QUERY, filmId);
    }

    @Override
    public void increaseUseful(long reviewId) {
        update(INCREASE_USEFUL_QUERY, reviewId);
    }

    @Override
    public void decreaseUseful(long reviewId) {
        update(DECREASE_USEFUL_QUERY, reviewId);
    }

    @Override
    public List<Review> getAll() {
        return findMany(GET_ALL_QUERY);
    }

    @Override
    public List<Review> getAllWithLimit(int limit) {
        return findMany(GET_ALL_WITH_LIMIT_QUERY, limit);
    }
}
