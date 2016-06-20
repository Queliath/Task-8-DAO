package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.domain.Person;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface PersonDAO {
    void addPerson(Person person) throws DAOException;
    void updatePerson(Person person) throws DAOException;
    void deletePerson(int id) throws DAOException;
    List<Person> getAllPersons() throws DAOException;
    Person getPersonById(int id) throws DAOException;
    List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType) throws DAOException;
}
