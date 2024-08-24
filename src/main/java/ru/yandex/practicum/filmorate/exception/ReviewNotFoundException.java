package ru.yandex.practicum.filmorate.exception;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException(long id) {
        super(id);
        message = "Ревью с id={} не найдено";
    }
}
