package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public List<DirectorDto> getALl() {
        log.info("Get all directors");
        return directorService.getAll();
    }

    @PostMapping
    public DirectorDto createDirector(@RequestBody @Valid DirectorDto directorDto) {
        log.info("Создание режиссера {}", directorDto);
        return directorService.save(directorDto);
    }

    @PutMapping
    public DirectorDto updateDirector(@RequestBody @Valid DirectorDto directorDto) {
        log.info("Обновление режиссера {}", directorDto);
        return directorService.update(directorDto);
    }


    @GetMapping("/{id}")
    public DirectorDto getDirectorById(@PathVariable long id) {
        log.info("Get director by id = {}", id);
        return directorService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDirectorById(@PathVariable long id) {
        log.info("Delete director by id = {}", id);
        directorService.delete(id);
    }
}
