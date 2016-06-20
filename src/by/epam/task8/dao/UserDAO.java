package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.User;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface UserDAO {
    void addUser(User user) throws DAOException;
    void updateUser(User user) throws DAOException;
    void deleteUser(int id) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    User getUserById(int id) throws DAOException;
    User getUserByEmail(String email) throws DAOException;
    List<User> getUsersByStatus(String status) throws DAOException;
}
