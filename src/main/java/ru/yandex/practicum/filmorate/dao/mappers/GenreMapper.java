package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genre genre);

    List<GenreDto> toDto(List<Genre> genres);

    Genre toEntity(GenreDto genreDto);

    List<Genre> toEntity(List<GenreDto> genresDto);
}
