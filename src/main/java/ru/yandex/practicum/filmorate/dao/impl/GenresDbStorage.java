package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.GenresStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class GenresDbStorage extends BaseDbStorage<Genre> implements GenresStorage {
    private RowMapper<Genre> genreIdRowMapper;

    private static final String FIND_ALL_QUERY = "select * from genres";
    private static final String FIND_BY_ID_QUERY = "select * from genres where id = ?";
    private static final String FIND_BY_FILM_ID_QUERY = "SELECT fg.genre_id, g.name FROM film_genres AS fg " +
            "JOIN genres AS g ON fg.genre_id = g.id WHERE film_id = ?";

    public GenresDbStorage(JdbcTemplate jdbcTemplate,
                           @Qualifier("genresRowMapper") RowMapper<Genre> rowMapper,
                           @Qualifier("genreIdRowMapper") RowMapper<Genre> genreIdRowMapper) {
        super(jdbcTemplate, rowMapper);
        this.genreIdRowMapper = genreIdRowMapper;
    }

    @Override
    public List<Genre> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Genre> findById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<Genre> findByFilmId(long filmId) {
        return jdbcTemplate.query(FIND_BY_FILM_ID_QUERY, genreIdRowMapper, filmId);
    }
}
