package ru.yandex.practicum.filmorate.dao.storageInterface;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenresStorage {
    List<Genre> findAll();

    Optional<Genre> findById(int id);

    List<Genre> findByFilmId(long filmId);

    void removeByFilmId(long filmId);
}
