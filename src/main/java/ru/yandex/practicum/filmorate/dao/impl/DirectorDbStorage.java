package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.DirectorStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class DirectorDbStorage extends BaseDbStorage<Director> implements DirectorStorage {

    private static final String UPDATE_QUERY = "UPDATE directors SET name = ? WHERE director_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM directors WHERE director_id = ?; " +
            "DELETE FROM director_films WHERE director_id = ?;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM directors WHERE director_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM directors";
    private static final String FIND_BY_FILM_ID_QUERY =
            "SELECT * FROM directors WHERE director_id in (SELECT director_id FROM director_films WHERE film_id = ?)";
    private static final String INSERT_QUERY = "INSERT INTO directors (name) VALUES (?)";
    private static final String REMOVE_BY_FILM_ID_QUERY = "DELETE FROM director_films WHERE film_id = ?";

    public DirectorDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Director> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Director save(Director director) {
        long id = insert(INSERT_QUERY, director.getName());
        director.setId(id);
        return director;
    }

    @Override
    public Director update(Director director) {
        long id = director.getId();
        update(UPDATE_QUERY, director.getName(), id);
        return director;
    }

    @Override
    public List<Director> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Director> findById(long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public List<Director> findDirectorsByFilmId(Long filmId) {
        return findMany(FIND_BY_FILM_ID_QUERY, filmId);
    }

    @Override
    public void deleteById(long id) {
        delete(DELETE_QUERY, id, id);
    }

    @Override
    public void removeByFilmId(long id) {
        delete(REMOVE_BY_FILM_ID_QUERY, id);
    }
}
