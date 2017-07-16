package com.ovladychenko.film.persistence;

/**
 * @author bvanchuhov
 */
public class FilmPersistenceException extends RuntimeException {

    public FilmPersistenceException(String message) {
        super(message);
    }

    public FilmPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmPersistenceException(Throwable cause) {
        super(cause);
    }
}
