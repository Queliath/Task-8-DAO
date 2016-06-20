package by.epam.task8.dao.impl.mysql;

import by.epam.task8.dao.MoviePersonRelationDAO;
import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.pool.mysql.MySQLConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLMoviePersonRelationDAO implements MoviePersonRelationDAO {
    @Override
    public void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movie_person_relation " +
                    "(movie_id, person_id, relation_type) VALUES (?, ?, ?)");
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to person with relation", e);
        }
    }

    @Override
    public void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie_person_relation " +
                    "WHERE movie_id = ? AND person_id = ? AND relation_type = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from person with relation", e);
        }
    }
}