package com.twu.biblioteca.library;

import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItemException;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class MovieTest {
    @Test
    public void shouldCreateABookWithItsYearDirectorAndMovieRating(){
        // Arrange
        Movie movie = new Movie("I Robot", 2016, "Director", 5);

        // Act
        String bookInfo = movie.getInfo();

        //Assert
        assertThat(bookInfo, is("I Robot,2016,Director,5"));
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItOut() throws RentItemException {
        // Arrange
        Movie movie = new Movie("I Robot", 2016, "Director", 5);

        // Act
        movie.setIsCheckOut(true);
        boolean isCheckOut = movie.getIsCheckOut();

        // Assert
        Assert.assertTrue(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedOut() throws RentItemException {
        // Arrange
        Movie movie = new Movie("I Robot", 2016, "Director", 5);

        // Act
        movie.setIsCheckOut(true);
        movie.setIsCheckOut(true);
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItIn() throws RentItemException {
        // Arrange
        Movie movie = new Movie("I Robot", 2016, "Director", 5);

        // Act
        movie.setIsCheckOut(true);
        movie.setIsCheckOut(false);
        boolean isCheckOut = movie.getIsCheckOut();

        // Assert
        Assert.assertFalse(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedIn() throws RentItemException {
        // Arrange
        Movie movie = new Movie("I Robot", 2016, "Director", 5);

        // Act
        movie.setIsCheckOut(false);


    }

}
