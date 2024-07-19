package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.utils.AfterFirstFilmDate;

import java.time.LocalDate;

public class AfterFirstFilmDateValidator implements ConstraintValidator <AfterFirstFilmDate, LocalDate> {
    private LocalDate firstFilm;
    @Override
    public void initialize(AfterFirstFilmDate constraintAnnotation) {
        firstFilm = LocalDate.parse(constraintAnnotation.date());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate != null && localDate.isAfter(firstFilm);
    }
}
