package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.FriendshipStorage;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

@Slf4j
@Repository
public class FriendshipDbStorage extends BaseDbStorage implements FriendshipStorage {
    private static final String FIND_FRIENDS_BY_USER_ID = "SELECT * FROM friendship WHERE user1_id = ?";
    private static final String CREATE_FRIENDSHIP_REQUEST = "INSERT INTO friendship (user1_id, user2_id) VALUES (?, ?)";
    private static final String DELETE_FRIENDSHIP_QUERY = "DELETE FROM friendship WHERE user1_id = ? AND user2_id = ?";
    private static final String CHECK_FRIENDSHIP_QUERY = "SELECT * FROM friendship WHERE user1_id = ? AND user2_id = ?";

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Friendship> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<Long> findFriendsIds(long userId) {
        List<Friendship> allFriendships = findMany(FIND_FRIENDS_BY_USER_ID, userId);
        return allFriendships.stream().map(Friendship::getUser2_id).toList();
    }

    @Override
    public void createFriendship(long userId, long friendId) {
        if (findOne(CHECK_FRIENDSHIP_QUERY, userId, friendId).isEmpty()) {
            insert(CREATE_FRIENDSHIP_REQUEST, userId, friendId);
        }
    }

    @Override
    public void deleteFriendship(long userId, long friendId) {
        delete(DELETE_FRIENDSHIP_QUERY, userId, friendId);
    }
}
