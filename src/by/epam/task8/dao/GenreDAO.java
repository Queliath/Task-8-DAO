package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.Genre;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface GenreDAO {
    void addGenre(Genre genre) throws DAOException;
    void updateGenre(Genre genre) throws DAOException;
    void deleteGenre(int id) throws DAOException;
    List<Genre> getAllGenres() throws DAOException;
    Genre getGenreById(int id) throws DAOException;
    List<Genre> getGenresByMovie(int movieId) throws DAOException;
}
