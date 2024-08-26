package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.utils.AfterFirstFilmDate;

import java.time.LocalDate;
import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDto {
    private Long id;
    @NotBlank(message = "Название не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Превышена максимальная длина описания")
    private String description;
    @AfterFirstFilmDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность должна быть положительным числом")
    private int duration;
    @Valid
    private List<GenreDto> genres;
    @Valid
    private MpaDto mpa = new MpaDto();
    private Set<Long> likes = new HashSet<>();

    private List<DirectorDto> directors = new ArrayList<>();

    public @Valid List<GenreDto> getGenres() {
        return genres == null ? Collections.emptyList() : genres;
    }
}
