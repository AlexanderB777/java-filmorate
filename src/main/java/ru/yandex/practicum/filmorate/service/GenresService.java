package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresStorage;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenresService {
    private final GenresStorage genresStorage;

    public List<Genre> findAll() {
        return genresStorage.findAll();
    }
    public Genre findById(int id) {
        Optional<Genre> genre = genresStorage.findById(id);
        if (genre.isPresent()) {
            return genre.get();
        } else {
            throw new GenreNotFoundException((long) id);
        }
    }
}
