package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorStorage {
    Director save(Director director);

    Director update(Director director);

    List<Director> getAll();

    Director findById(int id);

    void deleteById(int id);
}
