package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.Rating;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface RatingDAO {
    void addRating(Rating rating) throws DAOException;
    void updateRating(Rating rating) throws DAOException;
    void deleteRating(int movieId, int userId) throws DAOException;
    Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException;
    List<Rating> getRatingsByMovie(int movieId) throws DAOException;
    List<Rating> getRatingsByUser(int userId) throws DAOException;
}
