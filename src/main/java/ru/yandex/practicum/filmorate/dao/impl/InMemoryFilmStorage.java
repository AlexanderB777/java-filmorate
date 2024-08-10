package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

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
    public Optional<Film> findById(Long id) {
        log.info("Поиск фильма по id: {}", id);
        return Optional.ofNullable(films.get(id));
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

    @Override
    public void putLike(long id, long userId) {
        findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id))
                .getLikes()
                .add(userId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        findById(filmId)
                .orElseThrow(() -> new FilmNotFoundException(filmId))
                .getLikes()
                .remove(userId);
    }
}
