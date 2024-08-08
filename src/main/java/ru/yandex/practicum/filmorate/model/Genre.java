package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Genre {
    @Max(6)
    @Positive
    private int id;
}
