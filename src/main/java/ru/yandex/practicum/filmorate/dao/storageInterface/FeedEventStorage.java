package ru.yandex.practicum.filmorate.dao.storageInterface;

import ru.yandex.practicum.filmorate.model.FeedEvent;

import java.util.List;

public interface FeedEventStorage {
    List<FeedEvent> getFeedEvents(long userId);

    void addFeedEvent(FeedEvent feedEvent);

    void removeByUserId(long userId);
}
