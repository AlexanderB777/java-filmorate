package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ReviewDto create(@RequestBody @Valid ReviewDto reviewDto) {
        log.debug("Получен запрос на создание новой оценки: {}", reviewDto);
        return reviewService.create(reviewDto);
    }

    @PutMapping
    public ReviewDto update(@RequestBody @Valid ReviewDto reviewDto) {
        log.debug("Получен запрос на обновление ревью {}", reviewDto);
        return reviewService.update(reviewDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable long id) {
        log.debug("Получен запрос на удаление ревью с id = {}", id);
        reviewService.delete(id);
    }

    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable long id) {
        log.debug("Получен запрос на вывод ревью по id = {}", id);
        return reviewService.findById(id);
    }

    @GetMapping
    public List<ReviewDto> getReviewsByFilmId(
            @RequestParam long filmId,
            @RequestParam(defaultValue = "0") int count) {
        log.debug("Получен запрос на получение списка ревью к фильму с id={} в количестве = {}", filmId, count);
        return reviewService.getReviewsByFilmId(filmId, count);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public ReviewDto putLike(@PathVariable long reviewId, @PathVariable long userId) {
        log.debug("Получен запрос на добавление лайка отзыву с id = {} пользователем с id = {}", reviewId, userId);
        return reviewService.putLike(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public ReviewDto putDislike(@PathVariable long reviewId, @PathVariable long userId) {
        log.debug("Получен запрос на добавление дизлайка отзыву с id = {} пользователем с id = {}", reviewId, userId);
        return reviewService.putDislike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public ReviewDto deleteLike(@PathVariable long reviewId, @PathVariable long userId) {
        log.debug("Получен запрос на удаление лайка у отзыва с id = {} пользователем с id = {}", reviewId, userId);
        return reviewService.deleteLike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public ReviewDto deleteDislike(@PathVariable long reviewId, @PathVariable long userId) {
        log.debug("Получен запрос на удаление дизлайка у отзыва с id = {} пользователем с id = {}", reviewId, userId);
        return reviewService.deleteDislike(reviewId, userId);
    }
}
