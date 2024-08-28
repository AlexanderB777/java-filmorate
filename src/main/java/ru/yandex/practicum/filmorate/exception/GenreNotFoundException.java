package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends NotFoundException{
    public GenreNotFoundException(int id) {
        super(id);
        description = "Жанр с id=%d не найден".formatted(id);
    }
}
