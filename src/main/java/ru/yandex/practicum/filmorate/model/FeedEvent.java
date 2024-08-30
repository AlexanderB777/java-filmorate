package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.utils.TimestampSerializer;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class FeedEvent {

    public FeedEvent(long userId,
                     long entityId,
                     EventType eventType,
                     Operation operation) {
        this.userId = userId;
        this.entityId = entityId;
        this.eventType = eventType;
        this.operation = operation;
    }

    private Long eventId;
    private Long userId;
    private Long entityId;
    private EventType eventType;
    private Operation operation;
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp timestamp;
}
