package com.twu.biblioteca.library;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class RentItemTest {
    RentItem rentItem;
    LibraryUser user;

    @Before
    public void setUp() throws UserException {
        this.rentItem = new RentItem(new Book("I Robot", 1959, "Isaac Asimov"));
        this.user = new LibraryUser("111-1111", "password", "name", "email", "094");
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
        this.rentItem.setIsCheckOut(true, user);
        boolean isCheckOut = this.rentItem.getIsCheckOut();

        // Assert
        Assert.assertTrue(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedOut() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true, user);
        this.rentItem.setIsCheckOut(true, user);
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItIn() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true, user);
        this.rentItem.setIsCheckOut(false, user);
        boolean isCheckOut = this.rentItem.getIsCheckOut();

        // Assert
        Assert.assertFalse(isCheckOut);
    }

    @Test(expected = RentItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedIn() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(false, user);
    }

    @Test
    public void shouldChangeCurrentReservingUserWhenCheckingItOut() throws RentItemException {
        // Act
        this.rentItem.setIsCheckOut(true, user);

        // Assert
        assertThat(this.rentItem.getCurrentReservingUser(), is(this.user));
    }
}
