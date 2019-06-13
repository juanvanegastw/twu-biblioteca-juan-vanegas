package com.twu.biblioteca.library.item;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.item.RentalItem;
import com.twu.biblioteca.library.item.RentalItemException;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class RentalItemTest {
    RentalItem rentalItem;
    LibraryUser user;

    @Before
    public void setUp() throws UserException {
        this.rentalItem = new RentalItem(new Book("I Robot", 1959, "Isaac Asimov"));
        this.user = new LibraryUser("111-1111", "password", "name", "email", "094");
    }

    @Test
    public void shouldCreateABookWithItsPublicationYearAndAuthor(){
        // Act
        String bookInfo = this.rentalItem.getInfo();

        //Assert
        assertThat(bookInfo, is("I Robot,Isaac Asimov,1959"));
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItOut() throws RentalItemException {
        // Act
        this.rentalItem.setIsCheckOut(true, user);
        boolean isCheckOut = this.rentalItem.getIsCheckOut();

        // Assert
        Assert.assertTrue(isCheckOut);
    }

    @Test(expected = RentalItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedOut() throws RentalItemException {
        // Act
        this.rentalItem.setIsCheckOut(true, user);
        this.rentalItem.setIsCheckOut(true, user);
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItIn() throws RentalItemException {
        // Act
        this.rentalItem.setIsCheckOut(true, user);
        this.rentalItem.setIsCheckOut(false, user);
        boolean isCheckOut = this.rentalItem.getIsCheckOut();

        // Assert
        Assert.assertFalse(isCheckOut);
    }

    @Test(expected = RentalItemException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedIn() throws RentalItemException {
        // Act
        this.rentalItem.setIsCheckOut(false, user);
    }

    @Test
    public void shouldChangeCurrentReservingUserWhenCheckingItOut() throws RentalItemException {
        // Act
        this.rentalItem.setIsCheckOut(true, user);

        // Assert
        assertThat(this.rentalItem.getCurrentReservingUser(), is(this.user));
    }
}
