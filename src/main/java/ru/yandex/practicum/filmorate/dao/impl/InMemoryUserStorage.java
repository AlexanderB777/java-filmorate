package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user.getName());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        log.info("Получение списка всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(Long id) {
        log.info("Поиск пользователя по id: {}", id);
        if (!users.containsKey(id)) {
            log.info("Пользователя с ID: {} не существует", id);
            throw new UserNotFoundException(id);
        } else {
            log.info("Пользователь с ID {} найден", id);
            return users.get(id);
        }
    }

    @Override
    public Long findMaxId() {
        log.info("Поиск максимального значения Id среди существующих пользователей");
        return users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
    }
}
