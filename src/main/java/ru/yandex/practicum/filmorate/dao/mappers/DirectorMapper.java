package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDto toDto(Director director);

    List<DirectorDto> toDto(List<Director> directors);

    Director toEntity(DirectorDto directorDto);

    List<Director> toEntity(List<DirectorDto> directorsDto);
}