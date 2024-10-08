package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface FriendshipStorage {
    List<Long> findFriendsIds(long userId);

    void createFriendship(long userId, long friendId);

    void deleteFriendship(long userId, long friendId);
}
