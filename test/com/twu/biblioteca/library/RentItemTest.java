package com.twu.biblioteca.library;
import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class RentItemTest {
    RentItem rentItem;
    @Before
    public void setUp(){
        this.rentItem = new RentItem(new Book("I Robot", 1959, "Isaac Asimov"));
    }

    @Test
    public void shouldCreateABookWithItsPublicationYearAndAuthor(){

        // Act
        String bookInfo = this.rentItem.getInfo();

        //Assert
        assertThat(bookInfo, is("I Robot,Isaac Asimov,1959"));
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItOut() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true);
        boolean isCheckOut = this.rentItem.getIsCheckOut();

        // Assert
        Assert.assertTrue(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedOut() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true);
        this.rentItem.setIsCheckOut(true);
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItIn() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true);
        this.rentItem.setIsCheckOut(false);
        boolean isCheckOut = this.rentItem.getIsCheckOut();

        // Assert
        Assert.assertFalse(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedIn() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(false);

    }

}
