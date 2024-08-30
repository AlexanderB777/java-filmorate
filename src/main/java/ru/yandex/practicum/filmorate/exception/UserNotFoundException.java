package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super(id);
        description = "Пользователь с id=%d не найден".formatted(id);
    }
}
