package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public List<Mpa> findAll() {
        return mpaStorage.findAll();
    }

    public Mpa findById(int id) {
        Optional<Mpa> mpa = mpaStorage.findById(id);
        if (mpa.isPresent()) {
            return mpa.get();
        } else {
            throw new MpaNotFoundException(id);
        }
    }

}
