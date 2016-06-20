package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.Movie;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface MovieDAO {
    void addMovie(Movie movie) throws DAOException;
    void updateMovie(Movie movie) throws DAOException;
    void deleteMovie(int id) throws DAOException;
    List<Movie> getAllMovies() throws DAOException;
    Movie getMovieById(int id) throws DAOException;
    List<Movie> getMoviesByGenre(int genreId) throws DAOException;
    List<Movie> getMoviesByCountry(int countryId) throws DAOException;
    List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType) throws DAOException;
}
