package ru.yandex.practicum.filmorate.dao.mappers;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.impl.DirectorDbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {

    private final DirectorDbStorage directorDbStorage;

    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        film.getMpa().setId(rs.getInt("mpa_id"));
        film.setDirectors(directorDbStorage.findDirectorsByFilmId(film.getId()));
        return film;
    }
}
