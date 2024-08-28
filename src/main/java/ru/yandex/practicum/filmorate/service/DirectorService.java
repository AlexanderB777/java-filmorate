package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.mappers.DirectorMapper;
import ru.yandex.practicum.filmorate.dao.storageInterface.DirectorStorage;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.exception.DirectorNotFoundException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorStorage directorStorage;
    private final DirectorMapper directorMapper;

    public DirectorDto save(DirectorDto directorDto) {
        return directorMapper.toDto(directorStorage.save(directorMapper.toEntity(directorDto)));
    }

    public DirectorDto update(DirectorDto directorDto) {
        long id = directorDto.getId();
        Director storedDirector = directorStorage.findById(id).orElseThrow(() -> new DirectorNotFoundException(id));
        storedDirector.setName(directorDto.getName());
        return directorMapper.toDto(directorStorage.update(storedDirector));
    }

    public void delete(long id) {
        directorStorage.deleteById(id);
    }

    public List<DirectorDto> getAll() {
        return directorMapper.toDto(directorStorage.getAll());
    }

    public DirectorDto findById(long id) {
        Director director = directorStorage.findById(id).orElseThrow(() -> new DirectorNotFoundException(id));
        return directorMapper.toDto(director);
    }

}
