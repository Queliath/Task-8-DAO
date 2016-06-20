package by.epam.task8.dao.pool;

import by.epam.task8.dao.pool.mysql.MySQLConnectionPool;
import by.epam.task8.dao.pool.mysql.MySQLConnectionPoolException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLConnectionPoolTest {

    @Test(expected = MySQLConnectionPoolException.class)
    public void getAndFreeConnectionTest1() throws IllegalAccessException, ClassNotFoundException, InstantiationException, SQLException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = mySQLConnectionPool.getConnection();
        mySQLConnectionPool.freeConnection(connection);
        mySQLConnectionPool.freeConnection(connection);
    }

    @Test(expected = MySQLConnectionPoolException.class)
    public void getAndFreeConnectionTest2() throws IllegalAccessException, ClassNotFoundException, InstantiationException, SQLException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = mySQLConnectionPool.getConnection();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/epam_movie_rating",
                "root", "root");
        mySQLConnectionPool.freeConnection(connection);
    }

    @Test(expected = MySQLConnectionPoolException.class)
    public void getAndFreeConnectionTest3() throws IllegalAccessException, ClassNotFoundException, InstantiationException, SQLException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = mySQLConnectionPool.getConnection();
        connection.close();
        mySQLConnectionPool.freeConnection(connection);
    }
}
