package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresStorage;
import ru.yandex.practicum.filmorate.dao.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenresService {
    private final GenresStorage genresStorage;
    private final GenreMapper genreMapper;

    public List<GenreDto> findAll() {
        return genreMapper.toDto(genresStorage.findAll());
    }

    public GenreDto findById(int id) {
        Genre genre = genresStorage.findById(id).orElseThrow(() -> new GenreNotFoundException(id));
        return genreMapper.toDto(genre);
    }
}
