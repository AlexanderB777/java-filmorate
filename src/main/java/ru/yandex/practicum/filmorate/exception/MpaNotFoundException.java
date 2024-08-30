package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends NotFoundException{
    public MpaNotFoundException(long id) {
        super(id);
        description = "Mpa с id=%d не найден".formatted(id);
    }
}
