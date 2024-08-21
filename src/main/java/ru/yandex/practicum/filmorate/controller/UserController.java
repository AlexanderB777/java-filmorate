package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        log.debug("Получен запрос на получение всех пользователей");
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        log.debug("Получен запрос на создание пользователя: {}", userDto);
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto) {
        log.debug("Получен запрос на обновление пользователя с ID: {}", userDto.getId());
        UserDto persistedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(persistedUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> createFriendship(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос на создание дружбы пользователя с id {} с пользователем с id {}", id, friendId);
        userService.createFriendship(id, friendId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> removeFriendship(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос на удаление дружбы пользователя с id {} с пользователем с id {}", id, friendId);
        userService.removeFriendship(id, friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDto>> showFriends(@PathVariable Long id) {
        log.debug("Получен запрос на отображение всех друзей пользователя с id {}",id);
        return ResponseEntity.ok(userService.getAllFriendsFromUser(id));
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public ResponseEntity<List<UserDto>> showCommonFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос на отображение списка общих друзей " +
                "пользователя с id {}, и пользователя с id {} ", id, friendId);
        return ResponseEntity.ok(userService.getCommonFriends(id, friendId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.debug("Получен запрос на удаление пользователя с ID: {}", userId);
        userService.removeUser(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        log.debug("Получен запрос на получение пользователя с ID: {}", userId);
        return userService.getUserById(userId);
    }
}