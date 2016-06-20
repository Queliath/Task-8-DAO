package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;

/**
 * Created by Владислав on 19.06.2016.
 */
public interface MovieCountryDAO {
    void addMovieToCountry(int movieId, int countryId) throws DAOException;
    void deleteMovieFromCountry(int movieId, int countryId) throws DAOException;
}
