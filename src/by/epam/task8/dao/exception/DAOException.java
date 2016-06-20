package by.epam.task8.dao.exception;

/**
 * Created by Владислав on 11.06.2016.
 */
public class DAOException extends Exception {
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
