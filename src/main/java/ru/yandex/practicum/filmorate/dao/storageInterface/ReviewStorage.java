package ru.yandex.practicum.filmorate.dao.storageInterface;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    Review save(Review review);

    Review update(Review review);

    Optional<Review> findById(long id);

    void delete(long id);

    List<Review> getByFilmIdWithLimit(long filmId, int limit);

    List<Review> getByFilmId(long filmId);

    void increaseUseful(long reviewId);

    void decreaseUseful(long reviewId);

    List<Review> getAll();

    List<Review> getAllWithLimit(int limit);
}
