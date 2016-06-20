package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Comment;
import by.epam.task8.domain.Rating;
import by.epam.task8.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class UserDAOTest {

    @Test
    public void addUserTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();

        User expectedUser = new User();
        expectedUser.setEmail("Test user email");
        expectedUser.setPassword("Test user password");
        expectedUser.setFirstName("Test user first name");
        expectedUser.setLastName("Test user last name");
        expectedUser.setDateOfRegistry(new GregorianCalendar(2016, 5, 19).getTime());
        expectedUser.setPhoto("Test user photo");
        expectedUser.setRating(10);
        expectedUser.setStatus("normal");

        userDAO.addUser(expectedUser);
        List<User> allUsers = userDAO.getAllUsers();
        User actualUser = allUsers.get(allUsers.size() - 1);

        userDAO.deleteUser(actualUser.getId());

        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assert.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        Assert.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        Assert.assertEquals(expectedUser.getDateOfRegistry(), actualUser.getDateOfRegistry());
        Assert.assertEquals(expectedUser.getPhoto(), actualUser.getPhoto());
        Assert.assertEquals(expectedUser.getRating(), actualUser.getRating());
        Assert.assertEquals(expectedUser.getStatus(), actualUser.getStatus());
    }

    @Test
    public void updateUserTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();

        User expectedUser = userDAO.getUserById(1);

        User rollbackUser = new User();
        rollbackUser.setId(expectedUser.getId());
        rollbackUser.setEmail(expectedUser.getEmail());
        rollbackUser.setPassword(expectedUser.getPassword());
        rollbackUser.setFirstName(expectedUser.getFirstName());
        rollbackUser.setLastName(expectedUser.getLastName());
        rollbackUser.setDateOfRegistry(expectedUser.getDateOfRegistry());
        rollbackUser.setPhoto(expectedUser.getPhoto());
        rollbackUser.setRating(expectedUser.getRating());
        rollbackUser.setStatus(expectedUser.getStatus());

        expectedUser.setEmail("Test user email");
        expectedUser.setPassword("Test user password");
        expectedUser.setFirstName("Test user first name");
        expectedUser.setLastName("Test user last name");
        expectedUser.setDateOfRegistry(new GregorianCalendar(2016, 5, 19).getTime());
        expectedUser.setPhoto("Test user photo");
        expectedUser.setRating(10);
        expectedUser.setStatus("normal");

        userDAO.updateUser(expectedUser);

        User actualUser = userDAO.getUserById(1);

        userDAO.updateUser(rollbackUser);

        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assert.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        Assert.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        Assert.assertEquals(expectedUser.getDateOfRegistry(), actualUser.getDateOfRegistry());
        Assert.assertEquals(expectedUser.getPhoto(), actualUser.getPhoto());
        Assert.assertEquals(expectedUser.getRating(), actualUser.getRating());
        Assert.assertEquals(expectedUser.getStatus(), actualUser.getStatus());
    }

    @Test
    public void deleteUserTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        RatingDAO ratingDAO = daoFactory.getRatingDAO();

        User rollbackUser = userDAO.getUserById(2);
        List<Comment> rollbackComments = commentDAO.getCommentsByUser(2);
        List<Rating> rollbackRatings = ratingDAO.getRatingsByUser(2);

        userDAO.deleteUser(2);

        User deletedUser = userDAO.getUserById(2);

        userDAO.addUser(rollbackUser);
        List<User> allUsers = userDAO.getAllUsers();
        rollbackUser = allUsers.get(allUsers.size() - 1);
        for(Comment comment : rollbackComments){
            comment.setUserId(rollbackUser.getId());
            commentDAO.addComment(comment);
        }
        for(Rating rating : rollbackRatings){
            rating.setUserId(rollbackUser.getId());
            ratingDAO.addRating(rating);
        }

        Assert.assertNull(deletedUser);
    }
}
