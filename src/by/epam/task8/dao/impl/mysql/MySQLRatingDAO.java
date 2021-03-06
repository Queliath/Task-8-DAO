package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.RatingDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLRatingDAO implements RatingDAO {
    @Override
    public void addRating(Rating rating) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rating " +
                    "(movie_id, user_id, value) VALUES (?, ?, ?)");
            statement.setInt(1, rating.getMovieId());
            statement.setInt(2, rating.getUserId());
            statement.setInt(3, rating.getValue());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateRating(Rating rating) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE rating " +
                    "SET value = ? WHERE movie_id = ? AND user_id = ?");
            statement.setInt(1, rating.getValue());
            statement.setInt(2, rating.getMovieId());
            statement.setInt(3, rating.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when updating rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteRating(int movieId, int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM rating WHERE movie_id = ? AND user_id = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rating WHERE movie_id = ? AND user_id = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();

            Rating rating = null;
            if(resultSet.next()){
                rating = new Rating();
                rating.setMovieId(resultSet.getInt(1));
                rating.setUserId(resultSet.getInt(2));
                rating.setValue(resultSet.getInt(3));
            }
            return rating;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Rating> getRatingsByMovie(int movieId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rating WHERE movie_id = ?");
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            List<Rating> ratingsByMovie = new ArrayList<>();
            while(resultSet.next()){
                Rating rating = new Rating();
                rating.setMovieId(resultSet.getInt(1));
                rating.setUserId(resultSet.getInt(2));
                rating.setValue(resultSet.getInt(3));

                ratingsByMovie.add(rating);
            }
            return ratingsByMovie;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Rating> getRatingsByUser(int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rating WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Rating> ratingsByUser = new ArrayList<>();
            while(resultSet.next()){
                Rating rating = new Rating();
                rating.setMovieId(resultSet.getInt(1));
                rating.setUserId(resultSet.getInt(2));
                rating.setValue(resultSet.getInt(3));

                ratingsByUser.add(rating);
            }
            return ratingsByUser;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
