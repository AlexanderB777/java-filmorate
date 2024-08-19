package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    Film toEntity(FilmDto filmDto);

    List<Film> toEntity(List<FilmDto> filmDtoList);

    FilmDto toDto(Film film);

    List<FilmDto> toDto(List<Film> filmList);
}
