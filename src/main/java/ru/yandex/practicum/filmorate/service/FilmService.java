package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.FilmByLikeComparator;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        log.info("Получен запрос на получение всех фильмов");
        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        film.setId(getNextId());
        film = filmStorage.save(film);
        return film;
    }

    public Film updateFilm(Film film) {
        Long id = film.getId();
        log.info("Получен запрос на обновление фильма с Id={}", id);
        Film storedFilm = filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
        log.info("Фильм с id={} найден", id);
        storedFilm.setName(film.getName());
        storedFilm.setDescription(film.getDescription());
        storedFilm.setReleaseDate(film.getReleaseDate());
        storedFilm.setDuration(film.getDuration());
        log.info("Фильм успешно обновлен");
        return filmStorage.save(storedFilm);
    }

    private long getNextId() {
        return filmStorage.findMaxId() + 1;
    }

    public List<Film> getPopularFilms(int count) {
        log.info("Вызван метод по поиску популярных фильмов в количестве {}", count);
        List<Film> all = filmStorage.findAll();
        List<Film> result = all.stream()
                .sorted(new FilmByLikeComparator().reversed())
                .limit(count)
                .toList();
        log.info("Список фильмов успешно сформирован");
        return result;
    }

    public void putLike(Long id, Long userId) {
        log.info("Вызван метод по добавлению лайка фильму с id={} пользователем с id={}", id, userId);
        userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("Фильм найден");
        filmStorage.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id))
                .getLikes()
                .add(userId);
        log.info("Лайк добавлен");
    }

    public void deleteLike(Long filmId, Long userId) {
        log.info("Вызван метод по удалению лайка фильму с id={} пользователем с id={}",filmId , userId);
        userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("Пользователь с id={} найден", userId);
        filmStorage.findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException(filmId))
                .getLikes()
                .remove(userId);
        log.info("Лайк удален");
    }
}