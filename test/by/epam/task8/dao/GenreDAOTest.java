package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.Genre;
import by.epam.task8.domain.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class GenreDAOTest {

    @Test
    public void addGenreTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        GenreDAO genreDAO = daoFactory.getGenreDAO();

        Genre expectedGenre = new Genre();
        expectedGenre.setName("Test genre name");
        expectedGenre.setDescription("Test genre description");

        genreDAO.addGenre(expectedGenre);
        List<Genre> allGenres = genreDAO.getAllGenres();
        Genre actualGenre = allGenres.get(allGenres.size() - 1);

        genreDAO.deleteGenre(actualGenre.getId());

        Assert.assertEquals(expectedGenre.getName(), actualGenre.getName());
        Assert.assertEquals(expectedGenre.getDescription(), actualGenre.getDescription());
    }

    @Test
    public void updateGenreTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        GenreDAO genreDAO = daoFactory.getGenreDAO();

        Genre expectedGenre = genreDAO.getGenreById(1);

        Genre rollbackGenre = new Genre();
        rollbackGenre.setId(expectedGenre.getId());
        rollbackGenre.setName(expectedGenre.getName());
        rollbackGenre.setDescription(expectedGenre.getDescription());

        expectedGenre.setName("Test genre name");
        expectedGenre.setDescription("Test genre description");

        genreDAO.updateGenre(expectedGenre);

        Genre actualGenre = genreDAO.getGenreById(1);

        genreDAO.updateGenre(rollbackGenre);

        Assert.assertEquals(expectedGenre.getName(), actualGenre.getName());
        Assert.assertEquals(expectedGenre.getDescription(), actualGenre.getDescription());
    }

    @Test
    public void deleteGenreTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        GenreDAO genreDAO = daoFactory.getGenreDAO();
        MovieDAO movieDAO = daoFactory.getMovieDAO();
        MovieGenreDAO movieGenreDAO = daoFactory.getMovieGenreDAO();

        Genre rollbackGenre = genreDAO.getGenreById(1);
        List<Movie> rollbackMovies = movieDAO.getMoviesByGenre(1);

        genreDAO.deleteGenre(1);

        Genre deletedGenre = genreDAO.getGenreById(1);

        genreDAO.addGenre(rollbackGenre);
        List<Genre> allGenres = genreDAO.getAllGenres();
        rollbackGenre = allGenres.get(allGenres.size() - 1);
        for(Movie movie : rollbackMovies){
            movieGenreDAO.addMovieToGenre(movie.getId(), rollbackGenre.getId());
        }

        Assert.assertNull(deletedGenre);
    }
}
