package ru.yandex.practicum.filmorate.dao.mappers.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikeRowMapper implements RowMapper<Like> {
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setId(rs.getLong("id"));
        like.setFilm_id(rs.getLong("film_id"));
        like.setUser_id(rs.getLong("user_id"));
        return like;
    }
}