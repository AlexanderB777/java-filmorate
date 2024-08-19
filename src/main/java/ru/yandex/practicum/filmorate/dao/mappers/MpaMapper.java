package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MpaMapper {
    Mpa toEntity(MpaDto mpaDto);

    MpaDto toDto(Mpa mpa);

    List<Mpa> toEntity(List<MpaDto> mpaDtoList);

    List<MpaDto> toDto(List<Mpa> mpaList);
}
