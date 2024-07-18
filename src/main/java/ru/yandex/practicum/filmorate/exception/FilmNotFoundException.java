package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends NotFoundException {
    public FilmNotFoundException(Long id) {
        super(id);
        message = "Фильм с id=%d не найден".formatted(id);
    }
}
