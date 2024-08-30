package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class ReviewLike {
    private long id;
    private long reviewId;
    private long userId;
    private boolean isPositive;
}
