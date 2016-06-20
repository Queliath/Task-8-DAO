package by.epam.task8.dao;

import by.epam.task8.dao.exception.DAOException;
import by.epam.task8.dao.factory.DAOFactory;
import by.epam.task8.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MovieDAOTest {

    @Test
    public void addMovieTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        MovieDAO movieDAO = daoFactory.getMovieDAO();

        Movie expectedMovie = new Movie();
        expectedMovie.setName("Test movie name");
        expectedMovie.setOriginalName("Test movie original name");
        expectedMovie.setYear(2016);
        expectedMovie.setTagline("Test movie tagline");
        expectedMovie.setBudget(3_000_000);
        expectedMovie.setPremiere(new GregorianCalendar(2016, 5, 19).getTime());
        expectedMovie.setLasting(120);
        expectedMovie.setAnnotation("Test movie annotation");
        expectedMovie.setImage("Test movie image");

        movieDAO.addMovie(expectedMovie);
        List<Movie> allMovies = movieDAO.getAllMovies();
        Movie actualMovie = allMovies.get(allMovies.size() - 1);

        movieDAO.deleteMovie(actualMovie.getId());

        Assert.assertEquals(expectedMovie.getName(), actualMovie.getName());
        Assert.assertEquals(expectedMovie.getOriginalName(), actualMovie.getOriginalName());
        Assert.assertEquals(expectedMovie.getYear(), actualMovie.getYear());
        Assert.assertEquals(expectedMovie.getTagline(), actualMovie.getTagline());
        Assert.assertEquals(expectedMovie.getBudget(), actualMovie.getBudget());
        Assert.assertEquals(expectedMovie.getPremiere(), actualMovie.getPremiere());
        Assert.assertEquals(expectedMovie.getLasting(), actualMovie.getLasting());
        Assert.assertEquals(expectedMovie.getAnnotation(), actualMovie.getAnnotation());
        Assert.assertEquals(expectedMovie.getImage(), actualMovie.getImage());
    }

    @Test
    public void updateMovieTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        MovieDAO movieDAO = daoFactory.getMovieDAO();

        Movie expectedMovie = movieDAO.getMovieById(1);

        Movie rollbackMovie = new Movie();
        rollbackMovie.setId(expectedMovie.getId());
        rollbackMovie.setName(expectedMovie.getName());
        rollbackMovie.setOriginalName(expectedMovie.getOriginalName());
        rollbackMovie.setYear(expectedMovie.getYear());
        rollbackMovie.setTagline(expectedMovie.getTagline());
        rollbackMovie.setBudget(expectedMovie.getBudget());
        rollbackMovie.setPremiere(expectedMovie.getPremiere());
        rollbackMovie.setLasting(expectedMovie.getLasting());
        rollbackMovie.setAnnotation(expectedMovie.getAnnotation());
        rollbackMovie.setImage(expectedMovie.getImage());

        expectedMovie.setName("Test movie name");
        expectedMovie.setOriginalName("Test movie original name");
        expectedMovie.setYear(2016);
        expectedMovie.setTagline("Test movie tagline");
        expectedMovie.setBudget(3_000_000);
        expectedMovie.setPremiere(new GregorianCalendar(2016, 5, 19).getTime());
        expectedMovie.setLasting(120);
        expectedMovie.setAnnotation("Test movie annotation");
        expectedMovie.setImage("Test movie image");

        movieDAO.updateMovie(expectedMovie);

        Movie actualMovie = movieDAO.getMovieById(1);

        movieDAO.updateMovie(rollbackMovie);

        Assert.assertEquals(expectedMovie.getName(), actualMovie.getName());
        Assert.assertEquals(expectedMovie.getOriginalName(), actualMovie.getOriginalName());
        Assert.assertEquals(expectedMovie.getYear(), actualMovie.getYear());
        Assert.assertEquals(expectedMovie.getTagline(), actualMovie.getTagline());
        Assert.assertEquals(expectedMovie.getBudget(), actualMovie.getBudget());
        Assert.assertEquals(expectedMovie.getPremiere(), actualMovie.getPremiere());
        Assert.assertEquals(expectedMovie.getLasting(), actualMovie.getLasting());
        Assert.assertEquals(expectedMovie.getAnnotation(), actualMovie.getAnnotation());
        Assert.assertEquals(expectedMovie.getImage(), actualMovie.getImage());
    }

    @Test
    public void deleteMovieTest() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        MovieDAO movieDAO = daoFactory.getMovieDAO();
        GenreDAO genreDAO = daoFactory.getGenreDAO();
        CountryDAO countryDAO = daoFactory.getCountryDAO();
        PersonDAO personDAO = daoFactory.getPersonDAO();
        MovieGenreDAO movieGenreDAO = daoFactory.getMovieGenreDAO();
        MovieCountryDAO movieCountryDAO = daoFactory.getMovieCountryDAO();
        MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        RatingDAO ratingDAO = daoFactory.getRatingDAO();

        Movie rollbackMovie = movieDAO.getMovieById(1);
        List<Genre> rollbackGenres = genreDAO.getGenresByMovie(1);
        List<Country> rollbackCountries = countryDAO.getCountriesByMovie(1);
        List<List<Person>> rollbackPersons = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            rollbackPersons.add(personDAO.getPersonsByMovieAndRelationType(1, i));
        }
        List<Comment> rollbackComments = commentDAO.getCommentsByMovie(1);
        List<Rating> rollbackRatings = ratingDAO.getRatingsByMovie(1);

        movieDAO.deleteMovie(1);

        Movie deletedMovie = movieDAO.getMovieById(1);

        movieDAO.addMovie(rollbackMovie);
        List<Movie> allMovies = movieDAO.getAllMovies();
        rollbackMovie = allMovies.get(allMovies.size() - 1);
        for(Genre genre : rollbackGenres){
            movieGenreDAO.addMovieToGenre(rollbackMovie.getId(), genre.getId());
        }
        for(Country country : rollbackCountries){
            movieCountryDAO.addMovieToCountry(rollbackMovie.getId(), country.getId());
        }
        for(int i = 0; i < 8; i++){
            List<Person> personList = rollbackPersons.get(i);
            for(Person person : personList){
                moviePersonRelationDAO.addMovieToPersonWithRelation(rollbackMovie.getId(), person.getId(), i + 1);
            }
        }
        for(Comment comment : rollbackComments){
            comment.setMovieId(rollbackMovie.getId());
            commentDAO.addComment(comment);
        }
        for(Rating rating : rollbackRatings){
            rating.setMovieId(rollbackMovie.getId());
            ratingDAO.addRating(rating);
        }

        Assert.assertNull(deletedMovie);
    }
}
