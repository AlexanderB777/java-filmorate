package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenresController {
    private final GenresService genresService;

    @GetMapping
    public List<GenreDto> findAll() {
        log.debug("Получен запрос на получение всех жанров");
        return genresService.findAll();
    }

    @GetMapping("/{id}")
    public GenreDto findById(@PathVariable int id) {
        log.debug("Получен запрос на получение жанра с id={}", id);
        return genresService.findById(id);
    }

}
