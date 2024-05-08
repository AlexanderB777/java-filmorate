package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    User save(User user);

    Collection<User> findAll();

    Optional<User> findById(Long id);

    Long findMaxId();
}
