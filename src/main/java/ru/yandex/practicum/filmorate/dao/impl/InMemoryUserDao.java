package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryUserDao implements UserDao {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user.getName());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей");
        return users.values();
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Поиск пользователя по id: {}", id);
        if (!users.containsKey(id)) {
            log.info("Пользователя с ID: {} не существует", id);
            return Optional.empty();
        } else {
            log.info("Пользователь с ID {} найден", id);
            return Optional.of(users.get(id));
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
