package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MpaDto {
    @Positive
    @Max(5)
    private int id;
    private String name;
}
