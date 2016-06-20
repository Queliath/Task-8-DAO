package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;

/**
 * Created by Владислав on 19.06.2016.
 */
public interface MoviePersonRelationDAO {
    void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
    void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
}
