package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseDbStorage<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final RowMapper<T> rowMapper;

    protected Optional<T> findOne(String query, Object... params) {
        try {
            T result = jdbcTemplate.queryForObject(query, rowMapper, params);
            log.debug("Фильм в базе найден");
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ignored) {
            log.debug("Фильм не найден в базе");
            return Optional.empty();
        }
    }

    protected List<T> findMany(String query, Object... params) {
        return jdbcTemplate.query(query, rowMapper, params);
    }

    protected void delete(String query, Object... params) {
        int rowsDeleted = jdbcTemplate.update(query, params);
        if (rowsDeleted == 0) {
            throw new RuntimeException("Данные для удаления не обнаружены");
        }
    }

    protected void update(String query, Object... params) {
        int rowsUpdated = jdbcTemplate.update(query, params);
        if (rowsUpdated ==0) {
            throw new RuntimeException("Не удалось обновить данные");
        }
    }

    protected long insert(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                preparedStatement.setObject(idx + 1, params[idx]);
            }
            return preparedStatement;
        }, keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        if (id != null) {
            return id;
        } else {
            throw new RuntimeException("Не удалось сохранить данные");
        }
    }

    protected Long findMaxId(String query, Object... params) {

        Long maxId = jdbcTemplate.queryForObject(query, Long.class);
        if (maxId == null) {
            return 0L;
        }
        return maxId;
    }
}
