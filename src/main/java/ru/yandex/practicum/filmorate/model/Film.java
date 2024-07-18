package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.utils.AfterFirstFilmDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NotBlank(message = "Название не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Превышена максимальная длина описания")
    private String description;
    @AfterFirstFilmDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность должна быть положительным числом")
    private int duration;

    private Set<Long> likes = new HashSet<>();
}
