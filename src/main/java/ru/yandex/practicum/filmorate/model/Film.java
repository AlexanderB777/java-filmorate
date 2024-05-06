package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.annotations.AfterFirstFilmDate;

import java.time.LocalDate;

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
}
