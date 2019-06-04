package com.twu.biblioteca;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LibraryTest {
    @Test
    public void shouldAllowToCreateALibraryWithItsName(){
        // Setting
        Library myLibrary = new Library("Awesome Library");

        // Executing
        String libraryName = myLibrary.getName();

        // Verifying
        assertThat(libraryName, is("Awesome Library"));

    }

    @Test
    public void shouldAllowToAddABook(){
        // Arrange
        Book firstBook = new Book("First Book");
        Library myLibrary = new Library("My Library");

        // Act
        boolean bookAdded = myLibrary.addBook(firstBook);

        // Assert
        assertThat(bookAdded, is(true));
    }


    @Test
    public void shouldReturnTheBooksQuantity(){
        // Arrange
        Library myLibrary = new Library("My Library");

        // Act
        Integer booksQuantity = myLibrary.getBooksQuantity();

        // Assert
        assertThat(booksQuantity, is(0));

    }

    @Test
    public void shouldIncreaseBooksQuantityWhenAddingABook(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        myLibrary.addBook(firstBook);

        // Act
        Integer booksQuantity = myLibrary.getBooksQuantity();

        // Assert
        assertThat(booksQuantity, is(1));
    }

    @Test
    public void shouldIncreaseBooksQuantityWhenAddingTwoBooks(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);

        // Act
        Integer booksQuantity = myLibrary.getBooksQuantity();

        // Assert
        assertThat(booksQuantity, is(2));
    }

    @Test
    public void shouldReturnAStringListWhenBookAdded(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        myLibrary.addBook(firstBook);

        // Act
        String OneBookList = myLibrary.getBookList();

        // Assert
        Integer linesNumber = OneBookList.split("/n").length;
        assertThat(linesNumber, is(1));
    }

    @Test
    public void shouldReturnAStringListWhenTwoBooksAdded(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);

        // Act
        String OneBookList = myLibrary.getBookList();

        // Assert
        Integer linesNumber = OneBookList.split("\n").length;
        assertThat(linesNumber, is(2));
    }
}


