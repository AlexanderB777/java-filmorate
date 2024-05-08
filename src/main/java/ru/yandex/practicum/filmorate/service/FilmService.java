package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmDao filmDao;

    public Collection<Film> findAll() {
        log.info("Получен запрос на получение всех фильмов");
        return filmDao.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        film.setId(getNextId());
        film = filmDao.save(film);
        return film;
    }

    public Film updateFilm(Film film) throws NotFoundException {

        Long id = film.getId();
        Optional<Film> optionalFilm = filmDao.findById(id);

        if (optionalFilm.isEmpty()) {
            log.warn("Фильм с ID {} не найден", id);
            throw new NotFoundException("Нет такого фильма");
        }

        Film storedFilm = optionalFilm.get();
        storedFilm.setName(film.getName());
        storedFilm.setDescription(film.getDescription());
        storedFilm.setReleaseDate(film.getReleaseDate());
        storedFilm.setDuration(film.getDuration());
        log.info("Фильм успешно обновлен");
        return filmDao.save(storedFilm);
    }

    private long getNextId() {
        return filmDao.findMaxId() + 1;
    }
}
