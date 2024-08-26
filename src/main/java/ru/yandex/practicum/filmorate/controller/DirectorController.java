package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public List<Director> getALl() {
        log.info("Get all directors");
        return directorService.getAll();
    }

    @PostMapping
    public Director createDirector(@RequestBody Director director) {
        log.info("Create director");
        return directorService.save(director);
    }

    @PutMapping
    public Director updateDirector(@RequestBody Director director) {
        log.info("Update director");
        return directorService.update(director);
    }


    @GetMapping("/{id}")
    public Director getDirectorById(@PathVariable int id) {
        log.info("Get director by id");
        return directorService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDirectorById(@PathVariable int id) {
        log.info("Delete director by id");
        directorService.delete(id);
    }
}
