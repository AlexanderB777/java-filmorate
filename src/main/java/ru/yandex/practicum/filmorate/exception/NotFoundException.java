package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends RuntimeException{
    long id;
    String message;
    public NotFoundException(long id) {
        this.id = id;
    }
}
