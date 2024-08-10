package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {
    boolean checkFriendship(long user1_id, long user2_id);

    void confirmFriendship(long userId, long friendId);

    void createFriendshipRequest(long userId, long friendId);

    List<Long> findFriendsIds(long userId);
}
