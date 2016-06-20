package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Country;
import by.epam.task8.domain.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class CountryDAOTest {

    @Test
    public void addCountryTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CountryDAO countryDAO = daoFactory.getCountryDAO();

        Country expectedCountry = new Country();
        expectedCountry.setName("Test country name");
        expectedCountry.setIcon("Test country icon");

        countryDAO.addCountry(expectedCountry);
        List<Country> allCountries = countryDAO.getAllCountries();
        Country actualCountry = allCountries.get(allCountries.size() - 1);

        countryDAO.deleteCountry(actualCountry.getId());

        Assert.assertEquals(expectedCountry.getName(), actualCountry.getName());
        Assert.assertEquals(expectedCountry.getIcon(), actualCountry.getIcon());
    }

    @Test
    public void updateCountryTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CountryDAO countryDAO = daoFactory.getCountryDAO();

        Country expectedCountry = countryDAO.getCountryById(1);

        Country rollbackCountry = new Country();
        rollbackCountry.setId(expectedCountry.getId());
        rollbackCountry.setName(expectedCountry.getName());
        rollbackCountry.setIcon(expectedCountry.getIcon());

        expectedCountry.setName("Test country name");
        expectedCountry.setIcon("Test country icon");

        countryDAO.updateCountry(expectedCountry);

        Country actualCountry = countryDAO.getCountryById(1);

        countryDAO.updateCountry(rollbackCountry);

        Assert.assertEquals(expectedCountry.getName(), actualCountry.getName());
        Assert.assertEquals(expectedCountry.getIcon(), actualCountry.getIcon());
    }

    @Test
    public void deleteCountryTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CountryDAO countryDAO = daoFactory.getCountryDAO();
        MovieDAO movieDAO = daoFactory.getMovieDAO();
        MovieCountryDAO movieCountryDAO = daoFactory.getMovieCountryDAO();

        Country rollbackCountry = countryDAO.getCountryById(7);
        List<Movie> rollbackMovies = movieDAO.getMoviesByCountry(7);

        countryDAO.deleteCountry(7);

        Country deletedCountry = countryDAO.getCountryById(7);

        countryDAO.addCountry(rollbackCountry);
        List<Country> allCountries = countryDAO.getAllCountries();
        rollbackCountry = allCountries.get(allCountries.size() - 1);
        for(Movie movie : rollbackMovies){
            movieCountryDAO.addMovieToCountry(movie.getId(), rollbackCountry.getId());
        }

        Assert.assertNull(deletedCountry);
    }
}
