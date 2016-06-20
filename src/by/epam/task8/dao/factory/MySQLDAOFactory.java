package by.epam.task8.dao.factory;

import by.epam.task8.dao.*;
import by.epam.task8.dao.impl.mysql.*;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLDAOFactory extends DAOFactory {

    private final MovieDAO mySQLMovieDAO = new MySQLMovieDAO();
    private final GenreDAO mySQLGenreDAO = new MySQLGenreDAO();
    private final CountryDAO mySQLCountryDAO = new MySQLCountryDAO();
    private final PersonDAO mySQLPersonDAO = new MySQLPersonDAO();
    private final UserDAO mySQLUserDAO = new MySQLUserDAO();
    private final RatingDAO mySQLRatingDAO = new MySQLRatingDAO();
    private final CommentDAO mySQLCommentDAO = new MySQLCommentDAO();
    private final MovieCountryDAO mySQLMovieCountryDAO = new MySQLMovieCountryDAO();
    private final MovieGenreDAO mySQLMovieGenreDAO = new MySQLMovieGenreDAO();
    private final MoviePersonRelationDAO mySQLMoviePersonRelationDAO = new MySQLMoviePersonRelationDAO();

    @Override
    public MovieDAO getMovieDAO() {
        return mySQLMovieDAO;
    }

    @Override
    public GenreDAO getGenreDAO() {
        return mySQLGenreDAO;
    }

    @Override
    public CountryDAO getCountryDAO() {
        return mySQLCountryDAO;
    }

    @Override
    public PersonDAO getPersonDAO() {
        return mySQLPersonDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return mySQLUserDAO;
    }

    @Override
    public RatingDAO getRatingDAO() {
        return mySQLRatingDAO;
    }

    @Override
    public CommentDAO getCommentDAO() {
        return mySQLCommentDAO;
    }

    @Override
    public MovieCountryDAO getMovieCountryDAO() {
        return mySQLMovieCountryDAO;
    }

    @Override
    public MovieGenreDAO getMovieGenreDAO() {
        return mySQLMovieGenreDAO;
    }

    @Override
    public MoviePersonRelationDAO getMoviePersonRelationDAO() {
        return mySQLMoviePersonRelationDAO;
    }
}
