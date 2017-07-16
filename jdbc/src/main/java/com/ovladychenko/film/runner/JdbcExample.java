package com.ovladychenko.film.runner;

import com.ovladychenko.film.model.Film;
import com.ovladychenko.film.model.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bvanchuhov
 */
public class JdbcExample {

    private static final String SQL_SELECT_ALL = "SELECT id, title, description, rating, release_year FROM film_db.film";

    public static void main(String[] args) {
        // 1. Create Connection
        // 2. Create Statement
        // 3. Execute SQL -> ResultSet
        // 4. Process ResultSet

        String url = "jdbc:mysql://localhost:3306/film_db";
        String username = "root";
        String password = "root";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();

            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
                List<Film> films = prepareFilms(resultSet);
                for (Film film : films) {
                    System.out.println(film);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Film> prepareFilms(ResultSet resultSet) throws SQLException {
        List<Film> films = new ArrayList<>();
        while (resultSet.next()) {
            Film film = prepareFilm(resultSet);
            films.add(film);
        }
        return films;
    }

    private static Film prepareFilm(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Rating rating = Rating.valueOf(resultSet.getString("rating"));
        int releaseYear = resultSet.getInt("release_year");

        return new Film(id, title, description, releaseYear, rating);
    }
}
