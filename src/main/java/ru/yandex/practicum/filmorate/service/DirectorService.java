package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DirectorStorage;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorStorage directorStorage;

    public Director save(Director director) {
        return directorStorage.save(director);
    }

    public Director update(Director director) {
        return directorStorage.update(director);
    }

    public void delete(int id) {
        directorStorage.deleteById(id);
    }

    public List<Director> getAll() {
        return directorStorage.getAll();
    }

    public Director getById(int id) {
        return directorStorage.findById(id);
    }

}
