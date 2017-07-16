package com.ovladychenko.film.persistence;

import com.ovladychenko.film.model.Film;
import com.ovladychenko.film.model.Rating;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bvanchuhov
 */
public class JdbcFilmDao implements FilmDao {

    private static final String SQL_SELECT_ALL = "SELECT id, title, description, release_year, rating FROM film_db.film";
    private static final String SQL_GET_BY_ID = "SELECT id, title, description, release_year, rating " +
            "FROM film_db.film " +
            "WHERE id=?";
    private static final String SQL_SAVE_FILM = "INSERT INTO film_db.film(title, description, release_year, rating) " +
            "VALUE (?, ?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM film_db.film WHERE id=?";

    private final DataSource dataSource;

    public JdbcFilmDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Film> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
                List<Film> films = prepareFilms(resultSet);
                return films;
            }
        } catch (SQLException e) {
            throw new FilmPersistenceException("Can't get all films", e);
        }
    }

    private List<Film> prepareFilms(ResultSet resultSet) throws SQLException {
        List<Film> films = new ArrayList<>();
        while (resultSet.next()) {
            Film film = prepareFilm(resultSet);
            films.add(film);
        }
        return films;
    }

    private Film prepareFilm(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Rating rating = Rating.valueOf(resultSet.getString("rating"));
        int releaseYear = resultSet.getInt("release_year");

        return new Film(id, title, description, releaseYear, rating);
    }

    @Override
    public Film getById(long id) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? prepareFilm(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new FilmPersistenceException("Can't get film by id " + id, e);
        }
    }

    @Override
    public Film save(Film film) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_FILM, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, film.getTitle());
                preparedStatement.setString(2, film.getDescription());
                preparedStatement.setInt(3, film.getReleaseYear());
                preparedStatement.setString(4, film.getRating().name());

                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    long newId = resultSet.getLong(1);
                    film.setId(newId);
                    return film;
                } else {
                    throw new FilmPersistenceException("Can't get generated id for a film: " + film);
                }
            }
        } catch (SQLException e) {
            throw new FilmPersistenceException("Can't save a film: " + film, e);
        }
    }

    @Override
    public Film delete(long id) {
        Film filmById = getById(id);
        if (filmById == null) {
            return null;
        }

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
                preparedStatement.setLong(1, id);

                preparedStatement.execute();
            }
            return filmById;
        }catch (SQLException e) {
            throw new FilmPersistenceException("Can't delete a film by id: " + id, e);
        }
    }
}
