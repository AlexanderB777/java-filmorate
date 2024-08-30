package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.util.Comparator;

public class FilmDtoByIdComparator implements Comparator<FilmDto> {
    @Override
    public int compare(FilmDto o1, FilmDto o2) {
        return o2.getId().compareTo(o1.getId());
    }
}
