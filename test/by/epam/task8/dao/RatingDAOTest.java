package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Rating;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Владислав on 19.06.2016.
 */
public class RatingDAOTest {

    @Test
    public void addRatingTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        RatingDAO ratingDAO = daoFactory.getRatingDAO();

        Rating expectedRating = new Rating();
        expectedRating.setMovieId(7);
        expectedRating.setUserId(2);
        expectedRating.setValue(10);

        ratingDAO.addRating(expectedRating);
        Rating actualRating = ratingDAO.getRatingByMovieAndUser(7, 2);

        ratingDAO.deleteRating(7, 2);

        Assert.assertEquals(expectedRating.getMovieId(), actualRating.getMovieId());
        Assert.assertEquals(expectedRating.getUserId(), actualRating.getUserId());
        Assert.assertEquals(expectedRating.getValue(), actualRating.getValue());
    }

    @Test
    public void updateRatingTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        RatingDAO ratingDAO = daoFactory.getRatingDAO();

        Rating expectedRating = ratingDAO.getRatingByMovieAndUser(3, 3);

        Rating rollbackRating = new Rating();
        rollbackRating.setMovieId(expectedRating.getMovieId());
        rollbackRating.setUserId(expectedRating.getUserId());
        rollbackRating.setValue(expectedRating.getValue());

        expectedRating.setValue(3);

        ratingDAO.updateRating(expectedRating);

        Rating actualRating = ratingDAO.getRatingByMovieAndUser(3, 3);

        ratingDAO.updateRating(rollbackRating);

        Assert.assertEquals(expectedRating.getMovieId(), actualRating.getMovieId());
        Assert.assertEquals(expectedRating.getUserId(), actualRating.getUserId());
        Assert.assertEquals(expectedRating.getValue(), actualRating.getValue());
    }

    @Test
    public void deleteRatingTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        RatingDAO ratingDAO = daoFactory.getRatingDAO();

        Rating rollbackRating = ratingDAO.getRatingByMovieAndUser(3, 3);

        ratingDAO.deleteRating(3, 3);

        Rating deletedRating = ratingDAO.getRatingByMovieAndUser(3, 3);

        ratingDAO.addRating(rollbackRating);

        Assert.assertNull(deletedRating);
    }
}
