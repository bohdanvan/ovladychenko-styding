package com.ovladychenko.film.model;

/**
 * @author bvanchuhov
 */
public class Film {

    private long id;
    private String title;
    private String description;
    private int releaseYear;
    private Rating rating;

    public Film(long id, String title, String description, int releaseYear, Rating rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public Film(String title, String description, int releaseYear, Rating rating) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public Film setId(long id) {
        this.id = id;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                '}';
    }
}
