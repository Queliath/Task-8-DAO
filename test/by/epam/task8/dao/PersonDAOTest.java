package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Movie;
import by.epam.task8.domain.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class PersonDAOTest {

    @Test
    public void addPersonTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PersonDAO personDAO = daoFactory.getPersonDAO();

        Person expectedPerson = new Person();
        expectedPerson.setName("Test person name");
        expectedPerson.setDateOfBirth(new GregorianCalendar(2016, 5, 19).getTime());
        expectedPerson.setPlaceOfBirth("Test person place of birth");
        expectedPerson.setPhoto("Test person photo");

        personDAO.addPerson(expectedPerson);
        List<Person> allPersons = personDAO.getAllPersons();
        Person actualPerson = allPersons.get(allPersons.size() - 1);

        personDAO.deletePerson(actualPerson.getId());

        Assert.assertEquals(expectedPerson.getName(), actualPerson.getName());
        Assert.assertEquals(expectedPerson.getDateOfBirth(), actualPerson.getDateOfBirth());
        Assert.assertEquals(expectedPerson.getPlaceOfBirth(), actualPerson.getPlaceOfBirth());
        Assert.assertEquals(expectedPerson.getPhoto(), actualPerson.getPhoto());
    }

    @Test
    public void updatePersonTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PersonDAO personDAO = daoFactory.getPersonDAO();

        Person expectedPerson = personDAO.getPersonById(1);

        Person rollbackPerson = new Person();
        rollbackPerson.setId(expectedPerson.getId());
        rollbackPerson.setName(expectedPerson.getName());
        rollbackPerson.setDateOfBirth(expectedPerson.getDateOfBirth());
        rollbackPerson.setPlaceOfBirth(expectedPerson.getPlaceOfBirth());
        rollbackPerson.setPhoto(expectedPerson.getPhoto());

        expectedPerson.setName("Test person name");
        expectedPerson.setDateOfBirth(new GregorianCalendar(2016, 5, 19).getTime());
        expectedPerson.setPlaceOfBirth("Test person place of birth");
        expectedPerson.setPhoto("Test person photo");

        personDAO.updatePerson(expectedPerson);

        Person actualPerson = personDAO.getPersonById(1);

        personDAO.updatePerson(rollbackPerson);

        Assert.assertEquals(expectedPerson.getName(), actualPerson.getName());
        Assert.assertEquals(expectedPerson.getDateOfBirth(), actualPerson.getDateOfBirth());
        Assert.assertEquals(expectedPerson.getPlaceOfBirth(), actualPerson.getPlaceOfBirth());
        Assert.assertEquals(expectedPerson.getPhoto(), actualPerson.getPhoto());
    }

    @Test
    public void deletePersonTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        PersonDAO personDAO = daoFactory.getPersonDAO();
        MovieDAO movieDAO = daoFactory.getMovieDAO();
        MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();

        Person rollbackPerson = personDAO.getPersonById(1);
        List<List<Movie>> rollbackMovies = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            rollbackMovies.add(movieDAO.getMoviesByPersonAndRelationType(1, i));
        }

        personDAO.deletePerson(1);

        Person deletedPerson = personDAO.getPersonById(1);

        personDAO.addPerson(rollbackPerson);
        List<Person> allPersons = personDAO.getAllPersons();
        rollbackPerson = allPersons.get(allPersons.size() - 1);
        for(int i = 0; i < 8; i++){
            List<Movie> movieList = rollbackMovies.get(i);
            for(Movie movie : movieList){
                moviePersonRelationDAO.addMovieToPersonWithRelation(movie.getId(), rollbackPerson.getId(), i + 1);
            }
        }

        Assert.assertNull(deletedPerson);
    }
}
