package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.Comment;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface CommentDAO {
    void addComment(Comment comment) throws DAOException;
    void updateComment(Comment comment) throws DAOException;
    void deleteComment(int id) throws DAOException;
    List<Comment> getAllComments() throws DAOException;
    Comment getCommentById(int id) throws DAOException;
    List<Comment> getCommentsByMovie(int movieId) throws DAOException;
    List<Comment> getCommentsByUser(int userId) throws DAOException;
}
