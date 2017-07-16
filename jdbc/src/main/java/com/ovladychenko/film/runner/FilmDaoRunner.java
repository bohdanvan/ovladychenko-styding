package com.ovladychenko.film.runner;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.ovladychenko.film.model.Film;
import com.ovladychenko.film.model.Rating;
import com.ovladychenko.film.persistence.FilmDao;
import com.ovladychenko.film.persistence.JdbcFilmDao;

import javax.sql.DataSource;

/**
 * @author bvanchuhov
 */
public class FilmDaoRunner {

    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        FilmDao filmDao = new JdbcFilmDao(dataSource);

        Film deletedFilm = filmDao.delete(1001L);
        System.out.println(deletedFilm);

        Film film = filmDao.getById(1001L);
        System.out.println(film); // null
    }

    private static void testSave(FilmDao filmDao) {
        Film film = new Film("Java", "Film about Java", 2010, Rating.R);
        Film filmWithId = filmDao.save(film);

        long id = filmWithId.getId();

        Film persistedFilm = filmDao.getById(id);
        System.out.println(persistedFilm);
    }

    private static DataSource createDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/film_db");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
