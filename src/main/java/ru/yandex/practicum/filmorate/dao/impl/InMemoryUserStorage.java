package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Repository
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final List<Friendship> friendshipList = new ArrayList<>();

    @Override
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user.getName());
        user.setId(findMaxId() + 1);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        log.info("Получение списка всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Поиск пользователя по id: {}", id);
        return Optional.ofNullable(users.get(id));
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

    @Override
    public boolean checkReverseFriendship(long userId, long friendId) {
        return friendshipList.stream().anyMatch(x -> x.getUser2_id() == userId && x.getUser1_id() == friendId);
    }


    @Override
    public void confirmFriendship(long userId, long friendId) {
        Friendship friendship = friendshipList.stream()
                .filter(x -> x.getUser2_id() == userId && x.getUser1_id() == friendId)
                        .findFirst().get();
        friendship.setConfirmed(true);
        users.get(userId).getFriends().add(friendId);
        users.get(friendId).getFriends().add(userId);
    }

    @Override
    public void createFriendshipRequest(long userId, long friendId) {
        friendshipList.add(new Friendship(friendshipList.size(), userId, friendId, false));
    }
}
