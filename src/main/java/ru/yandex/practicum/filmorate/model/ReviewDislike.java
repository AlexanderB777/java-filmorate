package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class ReviewDislike {
    private long id;
    private long reviewId;
    private long userId;
}
