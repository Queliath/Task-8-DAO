package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Comment;
import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class CommentDAOTest {

    @Test
    public void addCommentTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();

        Comment expectedComment = new Comment();
        expectedComment.setMovieId(1);
        expectedComment.setUserId(2);
        expectedComment.setTitle("Test comment title");
        expectedComment.setContent("Test comment content");
        expectedComment.setDateOfPublication(new GregorianCalendar(2016, 5, 19).getTime());

        commentDAO.addComment(expectedComment);
        List<Comment> allComments = commentDAO.getAllComments();
        Comment actualComment = allComments.get(allComments.size() - 1);

        commentDAO.deleteComment(actualComment.getId());

        Assert.assertEquals(expectedComment.getMovieId(), actualComment.getMovieId());
        Assert.assertEquals(expectedComment.getUserId(), actualComment.getUserId());
        Assert.assertEquals(expectedComment.getTitle(), actualComment.getTitle());
        Assert.assertEquals(expectedComment.getContent(), actualComment.getContent());
        Assert.assertEquals(expectedComment.getDateOfPublication(), actualComment.getDateOfPublication());
    }

    @Test
    public void updateCommentTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();

        Comment expectedComment = commentDAO.getCommentById(1);

        Comment rollbackComment = new Comment();
        rollbackComment.setId(expectedComment.getId());
        rollbackComment.setMovieId(expectedComment.getMovieId());
        rollbackComment.setUserId(expectedComment.getUserId());
        rollbackComment.setTitle(expectedComment.getTitle());
        rollbackComment.setContent(expectedComment.getContent());
        rollbackComment.setDateOfPublication(expectedComment.getDateOfPublication());

        expectedComment.setMovieId(1);
        expectedComment.setUserId(2);
        expectedComment.setTitle("Test comment title");
        expectedComment.setContent("Test comment content");
        expectedComment.setDateOfPublication(new GregorianCalendar(2016, 5, 19).getTime());

        commentDAO.updateComment(expectedComment);

        Comment actualComment = commentDAO.getCommentById(1);

        commentDAO.updateComment(rollbackComment);

        Assert.assertEquals(expectedComment.getMovieId(), actualComment.getMovieId());
        Assert.assertEquals(expectedComment.getUserId(), actualComment.getUserId());
        Assert.assertEquals(expectedComment.getTitle(), actualComment.getTitle());
        Assert.assertEquals(expectedComment.getContent(), actualComment.getContent());
        Assert.assertEquals(expectedComment.getDateOfPublication(), actualComment.getDateOfPublication());
    }

    @Test
    public void deleteCommentTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();

        Comment rollbackComment = commentDAO.getCommentById(1);

        commentDAO.deleteComment(1);

        Comment deletedComment = commentDAO.getCommentById(1);

        commentDAO.addComment(rollbackComment);

        Assert.assertNull(deletedComment);
    }

}
