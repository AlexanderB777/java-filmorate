package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dao.mappers.UserMapper;
import ru.yandex.practicum.filmorate.dao.storageInterface.UserStorage;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        log.info("Получение списка всех пользователей");
        return userMapper.toDto(userStorage.findAll());
    }

    public UserDto createUser(UserDto userDto) {
        if (StringUtils.isBlank(userDto.getName())) {
            userDto.setName(userDto.getLogin());
        }
        User user = userMapper.toEntity(userDto);
        log.info("Создание нового пользователя: {}", userDto);
        return userMapper.toDto(userStorage.save(user));
    }

    public UserDto updateUser(UserDto userDto) {
        Long id = userDto.getId();
        User foundUser = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        foundUser.setEmail(userDto.getEmail());
        foundUser.setLogin(userDto.getLogin());
        foundUser.setName(userDto.getName());
        foundUser.setBirthday(userDto.getBirthday());
        log.info("Обновление пользователя с ID {}", id);
        return userMapper.toDto(userStorage.update(foundUser));
    }

    @Transactional
    public void createFriendship(Long userId, Long friendId) {
        log.info("Пользователем с Id={} вызван запрос на дружбу с пользователем с Id={}", userId, friendId);
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        if (userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException(friendId);
        }
        userStorage.createFriendship(userId, friendId);
        log.info("Дружба создана");
    }

    public void removeFriendship(Long userId, Long friendId) {
        log.info("Вызван метод удаляющий дружбу между пользователями с Id={} и Id={}", userId, friendId);
        if (userStorage.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        if (userStorage.findById(friendId).isEmpty()) {
            throw new UserNotFoundException(friendId);
        }
        userStorage.removeFriendship(userId, friendId);
        log.info("Дружба удалена");
    }

    public List<UserDto> getAllFriendsFromUser(Long id) {
        log.info("Вызван метод для получения списка друзей пользователя с id={}", id);
        User user = userStorage.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        log.info("Пользователь с id = {} найден", id);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) return Collections.emptyList();
        return user.getFriends().stream()
                .map(userStorage::findById)
                .map(Optional::orElseThrow)
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDto> getCommonFriends(Long id, Long friendId) {
        log.info("Вызван метод по поиску общих друзей пользователей с id={} и id={}", id, friendId);
        Set<Long> commonFriends = userStorage
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .getFriends();
        commonFriends.retainAll(userStorage
                .findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId))
                .getFriends());
        return commonFriends.stream()
                .map(userStorage::findById)
                .map(Optional::orElseThrow)
                .map(userMapper::toDto)
                .toList();
    }
}
