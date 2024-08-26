package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
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
    public Collection<FilmDto> findAll() {
        log.debug("Получен запрос на получение всех фильмов");
        return filmService.findAll();
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody FilmDto filmDto) {
        log.debug("Получен запрос на создание фильма: {}", filmDto.getName());
        return filmService.createFilm(filmDto);
    }

    @GetMapping("/{id}")
    public FilmDto getFilm(@PathVariable Long id) {
        log.debug("Получен запрос на получение фильма с ID: {}", id);
        return filmService.getFilmById(id);
    }

    @PutMapping
    public ResponseEntity<FilmDto> update(@Valid @RequestBody FilmDto film) {
        log.debug("Получен запрос на обновление фильма с ID: {}", film.getId());
        return ResponseEntity.ok(filmService.updateFilm(film));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<FilmDto>> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.debug("Получен запрос на получение списка популярный фильмов в количестве {}", count);
        return ResponseEntity.ok(filmService.getPopularFilms(count));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> putLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен запрос на создание лайка фильму с id={} пользователем с Id={}", id, userId);
        filmService.putLike(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.debug("Получен запрос на удаление лайка фильму с id={} пользователем с Id={}", id, userId);
        filmService.deleteLike(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/director/{directorId}")
    public List<FilmDto> getFilmByDirectorId(@PathVariable int directorId, @RequestParam String sortBy) {
        return filmService.getFilmsByDirectorId(directorId, sortBy);
    }

    @GetMapping("/search")
    public List<FilmDto> getSearchResults(@RequestParam String query, @RequestParam String by) {
        log.info("Поступил запрос на получение результатов поиска по фильмам.");
        return filmService.getSearchResults(query, by);
    }
}
