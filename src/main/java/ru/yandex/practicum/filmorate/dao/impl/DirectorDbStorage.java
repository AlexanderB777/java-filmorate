package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DirectorStorage;
import ru.yandex.practicum.filmorate.dao.mappers.DirectorRowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DirectorDbStorage implements DirectorStorage {

    private final JdbcTemplate jdbc;
    private final DirectorRowMapper directorRowMapper;

    private static final String UPDATE_DIRECTOR = "UPDATE directors SET name = ?";
    private static final String DELETE_DIRECTOR = "DELETE FROM directors WHERE director_id = ?";
    private static final String SELECT_DIRECTOR_BY_ID = "SELECT * FROM directors WHERE director_id = ?";
    private static final String SELECT_ALL_DIRECTORS = "SELECT * FROM directors";
    private static final String SELECT_DIRECTORS_BY_FILM_ID = "SELECT * FROM directors WHERE director_id in (SELECT director_id FROM director_films WHERE film_id = ?)";


    @Override
    public Director save(Director director) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc).withTableName("directors").usingGeneratedKeyColumns("director_id");
        Number id = insert.executeAndReturnKey(convertToMap(director));
        director.setId(id.intValue());

        return director;
    }

    @Override
    public Director update(Director director) {
        findById(director.getId());
        jdbc.update(UPDATE_DIRECTOR, director.getName());
        return director;
    }

    @Override
    public List<Director> getAll() {
        return jdbc.query(SELECT_ALL_DIRECTORS, directorRowMapper);
    }

    @Override
    public Director findById(int id) {
        try {
            return jdbc.queryForObject(SELECT_DIRECTOR_BY_ID, directorRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id);
        }
    }

    public List<Director> findDirectorsByFilmId(Long filmId) {
        return jdbc.query(SELECT_DIRECTORS_BY_FILM_ID, directorRowMapper, filmId);
    }

    @Override
    public void deleteById(int id) {
        int rows = jdbc.update(DELETE_DIRECTOR, id);
        if (rows != 1) throw new NotFoundException(id);
    }

    private Map<String, Object> convertToMap(Director director) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", director.getId());
        map.put("name", director.getName());
        return map;
    }
}
