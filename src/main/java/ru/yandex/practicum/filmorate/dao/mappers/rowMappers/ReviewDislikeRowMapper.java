package ru.yandex.practicum.filmorate.dao.mappers.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.ReviewDislike;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReviewDislikeRowMapper implements RowMapper<ReviewDislike> {
    @Override
    public ReviewDislike mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewDislike reviewDislike = new ReviewDislike();
        reviewDislike.setId(rs.getLong("id"));
        reviewDislike.setReviewId(rs.getLong("review_id"));
        reviewDislike.setUserId(rs.getLong("user_id"));
        return reviewDislike;
    }
}
