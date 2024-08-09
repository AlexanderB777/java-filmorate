package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.dao.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    private static final String INSERT_QUERY = "INSERT INTO users (login, name, email, birthday) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? " +
            "WHERE id = ?";

    public UserDbStorage(JdbcTemplate jdbcTemplate, RowMapper<User> rowMapper) {
        super(jdbcTemplate, rowMapper);
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
        return List.of();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long findMaxId() {
        return 0L;
    }
}
