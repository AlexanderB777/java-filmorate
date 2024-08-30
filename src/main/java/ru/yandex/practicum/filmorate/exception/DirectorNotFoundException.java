package ru.yandex.practicum.filmorate.exception;

public class DirectorNotFoundException extends NotFoundException{
    public DirectorNotFoundException(long id) {
        super(id);
        description = "Режиссер с id=%d не найден".formatted(id);
    }
}
