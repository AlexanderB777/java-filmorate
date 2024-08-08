package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY = "select * from films";
    private static final String FIND_BY_ID_QUERY = "select * from films where id = ?";
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, release_date, " +
                                                                  "duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET username = ?, email = ?, birthday = ? WHERE id = ?";
    private static final String INSERT_GENRES_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES(?, ?)";

    public FilmDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Film> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public Film save(Film film) {
        log.info("Сохранение фильма с названием: {}", film.getName());
        long id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId()
                );
        for (Genre genre : film.getGenres()) {
            insert(INSERT_GENRES_QUERY, id, genre.getId());
        }
        film.setId(id);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return List.of();
    }

    @Override
    public Optional<Film> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long findMaxId() {
        return 0L;
    }
}
