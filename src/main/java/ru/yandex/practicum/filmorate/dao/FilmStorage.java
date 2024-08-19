package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film save(Film film);

    Film update(Film film);

    List<Film> findAll();

    Optional<Film> findById(Long id);

    Long findMaxId();

    void putLike(long id, long userId);

    void deleteLike(long filmId, long userId);
}
