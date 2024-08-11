package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        log.debug("Получен запрос на получение всех пользователей");
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.debug("Получен запрос на создание пользователя: {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        log.debug("Получен запрос на обновление пользователя с ID: {}", user.getId());
        User persistedUser = userService.updateUser(user);
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
    public ResponseEntity<List<User>> showFriends(@PathVariable Long id) {
        log.debug("Получен запрос на отображение всех друзей пользователя с id {}",id);
        return userService.getAllFriendsFromUser(id);
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public ResponseEntity<List<User>> showCommonFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.debug("Получен запрос на отображение списка общих друзей " +
                "пользователя с id {}, и пользователя с id {} ", id, friendId);
        return userService.getCommonFriends(id, friendId);
    }
}