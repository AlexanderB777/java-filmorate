package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.FriendshipStorage;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

@Repository
public class FriendshipDbStorage extends BaseDbStorage implements FriendshipStorage {
    private static final String FIND_FRIENDSHIP = "SELECT * FROM friendship WHERE user1_id = ? AND user2_id = ?";
    private static final String CONFIRM_FRIENDSHIP =
            "UPDATE friendship SET confirmed = TRUE WHERE user1_id = ? AND user2_id = ?";
    private static final String CREATE_FRIENDSHIP_REQUEST =
            "INSERT INTO friendship (user1_id, user2_id, confirmed) VALUES (?, ?, FALSE)";
    private static final String CONFIRMED_FRIENDSHIPS_WITH_USER_QUERY =
            "SELECT * FROM friendship WHERE user1_id = ? OR user2_id = ? AND confirmed = TRUE";

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Friendship> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }


    public boolean checkFriendship(long user1_id, long user2_id) {
        return findOne(FIND_FRIENDSHIP, user1_id, user2_id).isPresent();
    }

    public void confirmFriendship(long userId, long friendId) {
        update(CONFIRM_FRIENDSHIP, userId, friendId);
    }

    public void createFriendshipRequest(long userId, long friendId) {
        insert(CREATE_FRIENDSHIP_REQUEST, userId, friendId);
    }

    public List<Long> findFriendsIds(long userId) {
        List<Friendship> allFriendships = findMany(CONFIRMED_FRIENDSHIPS_WITH_USER_QUERY, userId, userId);
        return allFriendships.stream()
                .map(x -> {
            if (x.getUser1_id() == userId) {
                return x.getUser2_id();
            } else {
                return x.getUser1_id();
            }
        }).toList();
    }
}
