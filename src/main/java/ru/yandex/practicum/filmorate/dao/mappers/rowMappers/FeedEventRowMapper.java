package ru.yandex.practicum.filmorate.dao.mappers.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FeedEvent;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedEventRowMapper implements RowMapper<FeedEvent> {
    public FeedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        FeedEvent feedEvent = new FeedEvent();
        feedEvent.setEventId(rs.getLong("event_id"));
        feedEvent.setEntityId(rs.getLong("entity_id"));
        feedEvent.setEventType(EventType.valueOf(rs.getString("event_type")));
        feedEvent.setOperation(Operation.valueOf(rs.getString("operation")));
        feedEvent.setUserId(rs.getLong("user_id"));
        feedEvent.setTimestamp(rs.getTimestamp("timestamp"));
        return feedEvent;
    }
}
