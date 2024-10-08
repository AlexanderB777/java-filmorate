package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<MpaDto> findAll() {
        log.debug("Получен запрос на получение всех рейтингов");
        return mpaService.findAll();
    }

    @GetMapping("/{id}")
    public MpaDto findById(@PathVariable int id) {
        log.debug("Получен запрос на получение рейтинга с id={}", id);
        return mpaService.findById(id);
    }
}
