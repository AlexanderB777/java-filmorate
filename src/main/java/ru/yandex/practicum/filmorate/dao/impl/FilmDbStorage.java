package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmDbStorage implements FilmStorage {


    @Override
    public Film save(Film film) {
        log.info("Сохранение фильма с названием: {}", film.getName());
        return null;
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
