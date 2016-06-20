package by.epam.task8.dao.factory;

import by.epam.task8.dao.*;

/**
 * Created by Владислав on 11.06.2016.
 */
public abstract class DAOFactory {

    private static final int MY_SQL = 1;

    private static final String configFileURI = "by/epam/task8/dao/factory/ConfigDAO.xml";

    private static final DAOFactory mySQLDAOFactory = new MySQLDAOFactory();

    public abstract MovieDAO getMovieDAO();
    public abstract GenreDAO getGenreDAO();
    public abstract CountryDAO getCountryDAO();
    public abstract PersonDAO getPersonDAO();
    public abstract UserDAO getUserDAO();
    public abstract RatingDAO getRatingDAO();
    public abstract CommentDAO getCommentDAO();
    public abstract MovieCountryDAO getMovieCountryDAO();
    public abstract MovieGenreDAO getMovieGenreDAO();
    public abstract MoviePersonRelationDAO getMoviePersonRelationDAO();

    public static DAOFactory getDAOFactory(){
        int factoryType = readConfig();

        switch (factoryType) {
            case MY_SQL:
                return mySQLDAOFactory;
            default:
                return null;
        }
    }

    private static int readConfig(){
        return MY_SQL;
    }

}
