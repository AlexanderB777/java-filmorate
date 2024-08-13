package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GenreDto {
    @Max(6)
    @Positive
    private int id;
    private String name;
}
