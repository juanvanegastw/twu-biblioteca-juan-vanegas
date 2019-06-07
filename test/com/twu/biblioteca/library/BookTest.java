package com.twu.biblioteca.library;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class BookTest {
    @Test
    public void shouldCreateABookWithItsPublicationYearAndAuthor(){
        // Arrange
        Book myBook = new Book("I Robot", 1992, "Isaac Asimov");

        // Act
        String bookInfo = myBook.getInfo();

        //Assert
        assertThat(bookInfo, is("I Robot,Isaac Asimov,1992"));
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItOut() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException {
        // Arrange
        Book myBook = new Book("I Robot", 1959, "Isaac Asimos");

        // Act
        myBook.setIsCheckOut(true);
        boolean isCheckOut = myBook.getIsCheckOut();

        // Assert
        Assert.assertTrue(isCheckOut);
    }

    @Test(expected = BookAlreadyCheckedOutException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedOut() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Book myBook = new Book("I Robot", 1959, "Isaac Asimos");

        // Act
        myBook.setIsCheckOut(true);
        myBook.setIsCheckOut(true);
    }

    @Test
    public void shouldChangeCheckOutStateWhenCheckingItIn() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Book myBook = new Book("I Robot", 1959, "Isaac Asimos");

        // Act
        myBook.setIsCheckOut(true);
        myBook.setIsCheckOut(false);
        boolean isCheckOut = myBook.getIsCheckOut();

        // Assert
        Assert.assertFalse(isCheckOut);
    }

    @Test(expected = BookAlreadyCheckedInException.class)
    public void shouldRaiseExceptionWhenBookAlreadyCheckedIn() throws BookAlreadyCheckedInException, BookAlreadyCheckedOutException{
        // Arrange
        Book myBook = new Book("I Robot", 1959, "Isaac Asimos");

        // Act
        myBook.setIsCheckOut(false);


    }

}
