package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.storageInterface.FeedEventStorage;
import ru.yandex.practicum.filmorate.model.FeedEvent;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class FeedEventDbStorage extends BaseDbStorage implements FeedEventStorage {
    private final static String GET_ALL_BY_USER_ID_QUERY = "SELECT * FROM feed_events WHERE user_id = ?";
    private final static String INSERT_QUERY =
            "INSERT INTO feed_events (entity_id, event_type, operation, user_id) VALUES (?, ?, ?, ?)";
    private final static String REMOVE_BY_USER_ID_QUERY = "DELETE FROM feed_events WHERE user_id = ?";

    public FeedEventDbStorage(JdbcTemplate jdbcTemplate, RowMapper<FeedEvent> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<FeedEvent> getFeedEvents(long userId) {
        return findMany(GET_ALL_BY_USER_ID_QUERY, userId);
    }

    @Override
    public void addFeedEvent(FeedEvent feedEvent) {
        insertEventId(INSERT_QUERY,
                feedEvent.getEntityId(),
                feedEvent.getEventType().toString(),
                feedEvent.getOperation().toString(),
                feedEvent.getUserId());
    }

    @Override
    public void removeByUserId(long userId) {
        delete(REMOVE_BY_USER_ID_QUERY, userId);
    }

    private void insertEventId(String query, Object... params) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(query, PreparedStatement.NO_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                preparedStatement.setObject(idx + 1, params[idx]);
            }
            return preparedStatement;
        }, keyHolder);
    }
}