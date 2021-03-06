package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MovieDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLMovieDAO implements MovieDAO {

    @Override
    public void addMovie(Movie movie) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO movie (name, original_name, year, tagline, budget, premiere," +
                            "lasting, annotation, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getOriginalName());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getTagline());
            statement.setInt(5, movie.getBudget());
            statement.setDate(6, new Date(movie.getPremiere().getTime()));
            statement.setInt(7, movie.getLasting());
            statement.setString(8, movie.getAnnotation());
            statement.setString(9, movie.getImage());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateMovie(Movie movie) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE movie SET name = ?, original_name = ?, year = ?, tagline = ?," +
                            "budget = ?, premiere = ?, lasting = ?, annotation = ?, image = ? WHERE id = ?");
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getOriginalName());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getTagline());
            statement.setInt(5, movie.getBudget());
            statement.setDate(6, new Date(movie.getPremiere().getTime()));
            statement.setInt(7, movie.getLasting());
            statement.setString(8, movie.getAnnotation());
            statement.setString(9, movie.getImage());
            statement.setInt(10, movie.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteMovie(int id) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting movie", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getAllMovies() throws DAOException {
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movie");

            List<Movie> allMovies = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setOriginalName(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movie.setTagline(resultSet.getString(5));
                movie.setBudget(resultSet.getInt(6));
                movie.setPremiere(resultSet.getDate(7));
                movie.setLasting(resultSet.getInt(8));
                movie.setAnnotation(resultSet.getString(9));
                movie.setImage(resultSet.getString(10));

                allMovies.add(movie);
            }

            return allMovies;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Movie getMovieById(int id) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Movie movie = null;
            if(resultSet.next()){
                movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setOriginalName(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movie.setTagline(resultSet.getString(5));
                movie.setBudget(resultSet.getInt(6));
                movie.setPremiere(resultSet.getDate(7));
                movie.setLasting(resultSet.getInt(8));
                movie.setAnnotation(resultSet.getString(9));
                movie.setImage(resultSet.getString(10));
            }
            return movie;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                    "INNER JOIN movie_genre ON movie.id = movie_genre.movie_id WHERE movie_genre.genre_id = ? ");
            statement.setInt(1, genreId);
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByGenre = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setOriginalName(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movie.setTagline(resultSet.getString(5));
                movie.setBudget(resultSet.getInt(6));
                movie.setPremiere(resultSet.getDate(7));
                movie.setLasting(resultSet.getInt(8));
                movie.setAnnotation(resultSet.getString(9));
                movie.setImage(resultSet.getString(10));

                moviesByGenre.add(movie);
            }

            return moviesByGenre;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByCountry(int countryId) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                    "INNER JOIN movie_country ON movie.id = movie_country.movie_id WHERE movie_country.country_id = ? ");
            statement.setInt(1, countryId);
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByCountry = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setOriginalName(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movie.setTagline(resultSet.getString(5));
                movie.setBudget(resultSet.getInt(6));
                movie.setPremiere(resultSet.getDate(7));
                movie.setLasting(resultSet.getInt(8));
                movie.setAnnotation(resultSet.getString(9));
                movie.setImage(resultSet.getString(10));

                moviesByCountry.add(movie);
            }

            return moviesByCountry;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType) throws DAOException {
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
            PreparedStatement statement = connection.prepareStatement("SELECT movie.* FROM movie " +
                    "INNER JOIN movie_person_relation ON movie.id = movie_person_relation.movie_id WHERE " +
                    "movie_person_relation.person_id = ? AND movie_person_relation.relation_type = ?");
            statement.setInt(1, personId);
            statement.setInt(2, relationType);
            ResultSet resultSet = statement.executeQuery();

            List<Movie> moviesByPersonAndRelationType = new ArrayList<>();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getInt(1));
                movie.setName(resultSet.getString(2));
                movie.setOriginalName(resultSet.getString(3));
                movie.setYear(resultSet.getInt(4));
                movie.setTagline(resultSet.getString(5));
                movie.setBudget(resultSet.getInt(6));
                movie.setPremiere(resultSet.getDate(7));
                movie.setLasting(resultSet.getInt(8));
                movie.setAnnotation(resultSet.getString(9));
                movie.setImage(resultSet.getString(10));

                moviesByPersonAndRelationType.add(movie);
            }

            return moviesByPersonAndRelationType;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all movies", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

}
