package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DirectorDto {
    @Positive
    private int id;
    private String name;
}
