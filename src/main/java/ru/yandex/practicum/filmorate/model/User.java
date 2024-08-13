package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();
}
