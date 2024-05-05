package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей");
        return userDao.findAll();
    }

    public User createUser(User user) {
        user.setId(getNextId());
        if (StringUtils.isBlank(user.getName())) {
            user.setName(user.getLogin());
        }
        log.info("Создание нового пользователя: {}", user);
        return userDao.save(user);
    }

    public User updateUser(User user) {
        Long id = user.getId();
        Optional<User> optionalUser = userDao.findById(id);


        if (optionalUser.isEmpty()) {
            log.warn("Пользователь с ID {} не найден", id);
            throw new NotFoundException("Пользователь не найден");
        }

        User storedUser = optionalUser.get();
        storedUser.setEmail(user.getEmail());
        storedUser.setLogin(user.getLogin());
        storedUser.setName(user.getName());
        storedUser.setBirthday(user.getBirthday());
        log.info("Обновление пользователя с ID {}", id);
        return userDao.save(storedUser);
    }

    private long getNextId() {
        return userDao.findMaxId() + 1;
    }
}
