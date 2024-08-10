package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.BaseDbStorage;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MpaDbStorage extends BaseDbStorage<Mpa> implements MpaStorage {
    private static final String FIND_ALL_QUERY = "select * from Mpa";
    private static final String FIND_BY_ID_QUERY = "select * from Mpa where id = ?";

    public MpaDbStorage(JdbcTemplate jdbcTemplate, RowMapper<Mpa> rowMapper) {
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public List<Mpa> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Mpa> findById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }
}
