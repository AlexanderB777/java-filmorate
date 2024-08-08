package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Mpa {
    @Positive
    @Max(5)
    private int id;
}
