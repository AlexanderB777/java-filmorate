package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User save(User user);

    List<User> findAll();

    User findById(Long id);

    Long findMaxId();
}
