package ru.yandex.practicum.filmorate.exception;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException(long id) {
        super(id);
        description = "Ревью с id={} не найдено";
    }
}
