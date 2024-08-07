package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Получен запрос на получение всех фильмов");
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Получен запрос на создание фильма: {}", film.getName());
        return filmService.createFilm(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        log.debug("Получен запрос на обновление фильма с ID: {}", film.getId());
        return ResponseEntity.ok(filmService.updateFilm(film));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getTenPopularFilms() {
        log.debug("Получен запрос на топ 10 фильмов");
        return ResponseEntity.ok(filmService.getPopularFilms(10));
    }


    @GetMapping("/popular?count={count}")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.debug("Получен запрос на получение списка популярный фильмов в количестве %d".formatted(count));
        return ResponseEntity.ok(filmService.getPopularFilms(count));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> putLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен запрос на создание лайка фильму с id=%d пользователем с Id=%d".formatted(id, userId));
        filmService.putLike(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен запрос на удаление лайка фильму с id=%d пользователем с Id=%d".formatted(id, userId));
        filmService.deleteLike(id, userId);
        return ResponseEntity.noContent().build();
    }
}
