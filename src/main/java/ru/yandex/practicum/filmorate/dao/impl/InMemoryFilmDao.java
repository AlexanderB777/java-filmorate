package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryFilmDao implements FilmDao {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film save(Film film) {
        log.info("Сохранение фильма с названием: {}", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        log.info("Получение списка всех фильмов");
        return films.values();
    }


    @Override
    public Optional<Film> findById(Long id) {
        log.info("Поиск фильма по id: {}", id);
        if (films.containsKey(id)) {
            return Optional.of(films.get(id));
        }
        return Optional.empty();
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
