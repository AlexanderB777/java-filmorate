package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film save(Film film);

    List<Film> findAll();

    Film findById(Long id);

    Long findMaxId();
}
