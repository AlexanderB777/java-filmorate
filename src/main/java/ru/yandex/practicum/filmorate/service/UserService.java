package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        log.info("Получение списка всех пользователей");
        return userStorage.findAll();
    }

    public User createUser(User user) {
        if (StringUtils.isBlank(user.getName())) {
            user.setName(user.getLogin());
        }
        log.info("Создание нового пользователя: {}", user);
        return userStorage.save(user);
    }

    public User updateUser(User user) {
        Long id = user.getId();
        User foundedUser = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        foundedUser.setEmail(user.getEmail());
        foundedUser.setLogin(user.getLogin());
        foundedUser.setName(user.getName());
        foundedUser.setBirthday(user.getBirthday());
        log.info("Обновление пользователя с ID {}", id);
        return userStorage.save(foundedUser);
    }

    public void createFriendship(Long userId, Long friendId) {
        log.info("Пользователем с Id={} вызван запрос на дружбу с пользователем с Id={}",userId, friendId);
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        if (userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException(friendId);
        }
        if (userStorage.checkReverseFriendship(userId, friendId)) {
            log.debug("Найдена обратная заявка");
            userStorage.confirmFriendship(userId, friendId);
            log.info("Дружба создана");
        } else {
            userStorage.createFriendshipRequest(userId, friendId);
            log.debug("Создан запрос дружбы");
        }
        log.info("Дружба создана");
    }

    public void removeFriendship(Long id, Long friendId) {
        log.info("Вызван метод удаляющий дружбу между пользователями с Id=%d и Id=%d".formatted(id, friendId));
        User user = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("Пользователь с id={} найден", id);
        User friend = userStorage.findById(friendId).orElseThrow(() -> new UserNotFoundException(friendId));
        log.info("Пользователь с id={} найден", friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        log.info("Дружба удалена");
    }

    public ResponseEntity<List<User>> getAllFriendsFromUser(Long id) {
        log.info("Вызван метод для получения списка друзей пользователя с id={}", id);
        User user = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("Пользователь с id = {} найден", id);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(
                user.getFriends().stream()
                        .map(userStorage::findById)
                        .map(Optional::orElseThrow)
                        .toList());


    }

    public ResponseEntity<List<User>> getCommonFriends(Long id, Long friendId) {
        log.info("Вызван метод по поиску общих друзей пользователей с id=%d и id=%d".formatted(id, friendId));
        Set<Long> commonFriends = userStorage
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .getFriends();
        commonFriends.retainAll(userStorage
                .findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId))
                .getFriends());
        return ResponseEntity.ok(commonFriends.stream().
                map(userStorage::findById)
                .map(Optional::orElseThrow)
                .toList());
    }
}
