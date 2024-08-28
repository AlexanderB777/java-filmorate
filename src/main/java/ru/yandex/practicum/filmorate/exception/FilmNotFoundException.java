package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends NotFoundException {
    public FilmNotFoundException(Long id) {
        super(id);
        description = "Фильм с id=%d не найден".formatted(id);
    }
}
