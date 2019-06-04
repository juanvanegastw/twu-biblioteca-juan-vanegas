package com.twu.biblioteca;
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

}
