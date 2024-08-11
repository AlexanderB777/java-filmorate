package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.FriendshipStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    FriendshipStorage friendshipStorage;

    private static final String INSERT_QUERY =
            "INSERT INTO users (login, name, email, birthday) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String USER_MAX_ID_QUERY = "SELECT MAX(id) FROM users";
    private static final String DELETE_FRIENDSHIP_QUERY =
            "DELETE FROM friendship WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";

    public UserDbStorage(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper, FriendshipStorage friendshipStorage) {
        super(jdbcTemplate, rowMapper);
        this.friendshipStorage = friendshipStorage;
    }

    @Override
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user);
        if (user.getId() == null) {
            long id = insert(INSERT_QUERY,
                    user.getLogin(),
                    user.getName(),
                    user.getEmail(),
                    Date.valueOf(user.getBirthday()));
            user.setId(id);
        } else {
            update(UPDATE_QUERY,
                    user.getLogin(),
                    user.getName(),
                    user.getEmail(),
                    Date.valueOf(user.getBirthday()),
                    user.getId());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> userOptional = findOne(FIND_BY_ID_QUERY, id);
        userOptional.ifPresent(
                user -> user.setFriends(new HashSet<>(
                        friendshipStorage.
                                findFriendsIds(id))));
        return userOptional;
    }

    @Override
    public Long findMaxId() {
        return super.findMaxId(USER_MAX_ID_QUERY);
    }

    @Override
    public void removeFriendship(long userId, long friendId) {
        friendshipStorage.deleteFriendship(userId, friendId);
    }

    @Override
    public void createFriendship(long userId, long friendId) {
        friendshipStorage.createFriendship(userId, friendId);
    }

}
