package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.GenresStorage;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.dao.mappers.UserIdRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    final MpaStorage mpaDbStorage;
    final GenresStorage genresDbStorage;

    private static final String FIND_ALL_QUERY = "select * from films";
    private static final String FIND_BY_ID_QUERY = "select * from films where id = ?";
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, release_date, " +
            "duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, mpa_id =? WHERE id = ?";
    private static final String INSERT_GENRES_QUERY = "MERGE INTO film_genres (film_id, genre_id) " +
            "KEY (film_id, genre_id) VALUES (?, ?)";
    private static final String FILM_MAX_ID_QUERY = "SELECT MAX(id) FROM films";
    private static final String PUT_LIKE_QUERY = "INSERT INTO likes (film_id, user_id) VALUES(?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String GET_LIKES_FROM_FILM_QUERY = "SELECT user_id FROM likes WHERE film_id = ?";

    public FilmDbStorage(JdbcTemplate jdbcTemplate,
                         RowMapper<Film> rowMapper,
                         MpaStorage mpaDbStorage,
                         GenresStorage genresDbStorage) {
        super(jdbcTemplate, rowMapper);
        this.mpaDbStorage = mpaDbStorage;
        this.genresDbStorage = genresDbStorage;
    }

    @Override
    public Film save(Film film) {
        log.info("Сохранение фильма: {}", film);

        long id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(id);

        for (Long like : film.getLikes()) {
            putLike(id, like);
        }
        for (Genre genre : film.getGenres()) {
            insert(INSERT_GENRES_QUERY, id, genre.getId());
        }
        return film;
    }

    public Film update(Film film) {
        Long id = film.getId();
        update(UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                id);
        for (Long like : film.getLikes()) {
            putLike(id, like);
        }
        for (Genre genre : film.getGenres()) {
            insert(INSERT_GENRES_QUERY, id, genre.getId());
        }
        return film;
    }

    @Override
    public List<Film> findAll() {
        List<Film> allFilms = new ArrayList<Film>();
        for (Film film : findMany(FIND_ALL_QUERY)) {
            film.setLikes(new HashSet<>(getLikesFromFilm(film.getId())));
            allFilms.add(film);
        }
        return allFilms;
    }

    @Override
    public Optional<Film> findById(Long id) {
        log.debug("Выполение метода findById");
        Optional<Film> filmOptional = findOne(FIND_BY_ID_QUERY, id);

        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            log.debug("Фильм с id: {} найден", film.getId());
            film.setLikes(new HashSet<>(getLikesFromFilm(id)));
            log.debug("Лайки в найденный фильм добавлены");
            Mpa mpa = film.getMpa();
            int mpaId = mpa.getId();
            film.setMpa(mpaDbStorage.findById(mpaId).get());
            film.setGenres(genresDbStorage.findByFilmId(id));
            log.debug("Жанры для данного фильма установлены");
        }
        return filmOptional;
    }

    @Override
    public Long findMaxId() {
        log.debug("Поиск максимального id");
        return super.findMaxId(FILM_MAX_ID_QUERY);
    }

    @Override
    public void putLike(long id, long userId) {
        log.debug("Добавление лайка фильму с id={} пользователем с id={}", id, userId);
        insert(PUT_LIKE_QUERY, id, userId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        log.debug("Удаление лайка для фильма с id={}, пользователем с id={}", filmId, userId);
        delete(DELETE_LIKE_QUERY, filmId, userId);
    }

    private List<Long> getLikesFromFilm(long id) {
        log.debug("Поиск лайков для фильма с id: {}", id);
        return jdbcTemplate.query(GET_LIKES_FROM_FILM_QUERY, new UserIdRowMapper(), id);
    }
}
