package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();


    @Override
    public Film save(Film film) {
        log.info("Сохранение фильма с названием: {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> findAll() {
        log.info("Получение списка всех фильмов");
        System.out.println(films.values());
        return new ArrayList<>(films.values());
    }


    @Override
    public Film findById(Long id) {
        log.info("Поиск фильма по id: {}", id);
        if (!films.containsKey(id)) throw new FilmNotFoundException(id);
        return films.get(id);
    }

    @Override
    public Long findMaxId() {
        log.info("Поиск максимального значения Id среди существующих фильмов");
        return films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
    }
}
