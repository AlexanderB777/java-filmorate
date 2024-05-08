package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {
    Film save(Film film);

    Collection<Film> findAll();

    Optional<Film> findById(Long id);

    Long findMaxId();
}
