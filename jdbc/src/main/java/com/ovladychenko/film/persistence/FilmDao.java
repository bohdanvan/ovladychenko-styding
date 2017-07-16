package com.ovladychenko.film.persistence;

import com.ovladychenko.film.model.Film;

import java.util.List;

/**
 * @author bvanchuhov
 */
public interface FilmDao {

    /**
     * @throws FilmPersistenceException
     */
    List<Film> getAll();

    /**
     * @return a film by specified id or {@code null} if film is not found.
     * @throws FilmPersistenceException
     */
    Film getById(long id);

    /**
     * @throws FilmPersistenceException
     */
    Film save(Film film);

    /**
     * @throws FilmPersistenceException
     */
    Film delete(long id);
}
