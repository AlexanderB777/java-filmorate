package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DirectorMapper;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.dao.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.dao.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.FilmByLikeComparator;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final FilmMapper filmMapper;
    private final MpaMapper mpaMapper;
    private final DirectorMapper directorMapper;
    private final FilmDbStorage filmDbStorage;

    public Collection<FilmDto> findAll() {
        log.info("Получен запрос на получение всех фильмов");
        return filmMapper.toDto(filmStorage.findAll());
    }

    public FilmDto createFilm(FilmDto filmDto) {
        log.info("Получен запрос на создание фильма: {}", filmDto);
        Film film = filmMapper.toEntity(filmDto);
        return filmMapper.toDto(filmStorage.save(film));
    }

    public FilmDto updateFilm(FilmDto filmDto) {
        Long id = filmDto.getId();
        log.info("Получен запрос на обновление фильма: {}", filmDto);
        Film storedFilm = filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
        log.info("Фильм с id={} найден", filmDto.getId());
        storedFilm.setName(filmDto.getName());
        storedFilm.setDescription(filmDto.getDescription());
        storedFilm.setReleaseDate(filmDto.getReleaseDate());
        storedFilm.setDuration(filmDto.getDuration());
        storedFilm.setMpa(mpaMapper.toEntity(filmDto.getMpa()));
        storedFilm.setDirectors(directorMapper.toEntity(filmDto.getDirectors()));
        log.info("Фильм успешно обновлен");
        return filmMapper.toDto(filmStorage.update(storedFilm));
    }

    public List<FilmDto> getPopularFilms(int count) {
        log.info("Вызван метод по поиску популярных фильмов в количестве {}", count);
        List<Film> all = filmStorage.findAll();
        List<FilmDto> result = all.stream()
                .sorted(new FilmByLikeComparator().reversed())
                .limit(count)
                .map(filmMapper::toDto)
                .toList();
        log.info("Список фильмов успешно сформирован");
        return result;
    }

    public void putLike(long id, long userId) {
        log.info("Вызван метод по добавлению лайка фильму с id={} пользователем с id={}", id, userId);
        userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("Фильм найден");
        filmStorage.putLike(id, userId);
        log.info("Лайк добавлен");
    }

    public void deleteLike(Long filmId, Long userId) {
        log.info("Вызван метод по удалению лайка фильму с id={} пользователем с id={}", filmId, userId);
        userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("Пользователь с id={} найден", userId);
        filmStorage.deleteLike(filmId, userId);
        log.info("Лайк удален");
    }

    public FilmDto getFilmById(Long id) {
        Film film = filmStorage.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
        return filmMapper.toDto(film);
    }

    public List<FilmDto> getFilmsByDirectorId(int directorId, String sortBy) {
        List<Film> films = filmDbStorage.findFilmsByDirectorId(directorId);
        return switch (sortBy) {
            case "year" -> films.stream()
                    .sorted(Comparator.comparing(Film::getReleaseDate))
                    .map(filmMapper::toDto).toList();
            case "likes" -> films.stream()
                    .map(film -> filmDbStorage.findById(film.getId()))
                    .map(Optional::get)
                    .sorted(new FilmByLikeComparator().reversed())
                    .map(filmMapper::toDto)
                    .toList();
            default -> null;
        };
    }

    public List<FilmDto> getSearchResults(String query, String by) {
        System.out.println("search: " + query);
        System.out.println("type:" + by);
        return switch (by) {
            case "title" -> findAll().stream()
                    .map(filmDto -> getFilmById(filmDto.getId()))
                    .filter(film -> film.getName().toLowerCase().contains(query.toLowerCase()))
                    .toList();
            case "director" -> findAll().stream()
                    .map(filmDto -> getFilmById(filmDto.getId()))
                    .filter(film -> film.getDirectors().stream()
                            .map(DirectorDto::getName)
                            .anyMatch(name -> name.toLowerCase().contains(query.toLowerCase())))
                    .toList();
            case "director,title", "title,director" -> findAll().stream()
                    .map(filmDto -> getFilmById(filmDto.getId()))
                    .filter(film -> film.getName().toLowerCase().contains(query.toLowerCase())
                            || film.getDirectors().stream()
                            .map(DirectorDto::getName)
                            .anyMatch(name -> name.toLowerCase().contains(query.toLowerCase())))
                    .toList();
            default -> new ArrayList<>();
        };
    }
}