package com.twu.biblioteca;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class BookTest {
    @Test
    public void shouldAllowToCreateABookWithItsName(){
        // Arrange
        Book myBook = new Book("I Robot");

        // Act
        String bookName = myBook.getName();

        // Assert
        assertThat(bookName, is("I Robot"));

    }

    @Test
    public void shouldAllowToCreateAnotherBook(){
        // Arrange
        Book myBook = new Book("A Song of Fire and Ice");

        // Act
        String bookName = myBook.getName();

        // Assert
        assertThat(bookName, is("A Song of Fire and Ice"));
    }

    @Test
    public void shouldAllowToCreateABookWithItsPublicationYearAndAuthor(){
        // Arrange
        Book myBook = new Book("I Robot", 1992, "Isaac Asimov");

        // Act
        String bookInfo = myBook.getInfo();

        //Assert
        Assert.assertTrue(bookInfo.split(myBook.infoSeparator).length == 3);
    }

}
