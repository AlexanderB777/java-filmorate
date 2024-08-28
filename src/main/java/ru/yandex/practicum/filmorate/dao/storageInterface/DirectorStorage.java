package ru.yandex.practicum.filmorate.dao.storageInterface;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorStorage {
    Director save(Director director);

    Director update(Director director);

    List<Director> getAll();

    Optional<Director> findById(long id);

    void deleteById(long id);

    void removeByFilmId(long id);
}
