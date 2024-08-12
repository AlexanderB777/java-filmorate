package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User save(User user);

    User update(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    Long findMaxId();

    void removeFriendship(long userId, long friendId);

    void createFriendship(long userId, long friendId);
}
