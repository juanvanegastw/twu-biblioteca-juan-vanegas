package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LibraryTest {

    @Test
    /**
     * Allow es redundante
     */
    public void shouldAllowToCreateALibraryWithItsName(){
        // Setting
        Library myLibrary = new Library("Awesome Library");

        // Executing
        String libraryName = myLibrary.getName();

        // Verifying
        assertThat(libraryName, is("Awesome Library"));

    }

    @Test
    /**
     * Allow es redundante
     */
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
    /**
     * SUT solo es llamado en pruebas, ¿es necesario tener esta prueba?.
     */
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

        // Act
        myLibrary.addBook(firstBook);


        Integer actualBooksQuantity = myLibrary.getBooksQuantity();

        // Assert
        // Podrias definir claramanete los nombres del expected y el actual para que sea más legible
        int expectedBooksQuantity = 1;
        assertThat(actualBooksQuantity, is(expectedBooksQuantity));
    }

    @Test
    public void shouldIncreaseBooksQuantityWhenAddingTwoBooks(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");

        // Act
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);

        // Assert
        Integer booksQuantity = myLibrary.getBooksQuantity();
        // Podrias definir claramanete los nombres del expected y el actual para que sea más legible
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
        Integer linesNumber = OneBookList.split("\n").length;
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

    @Test
    public void shouldPrintBookIndexInfo(){
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        library.addBook(firstBook);
        library.addBook(secondBook);

        // Act
        String bookList = library.getBookList();

        // Assert
        String [] bookListArray = bookList.split("\n");
        int index = 0;
        for (String line: bookListArray){
            String[] lineFields = line.split(Book.infoSeparator);
            String firstItem = lineFields[0];
            assertThat(firstItem, is(String.valueOf(index)));
            index++;
        }
    }

    @Test
    /**
     * El nombre de la prueba no es decriptivo frente a lo que esta probando
     */
    public void shouldSetAsReservedABookWhenCheckingOutBook() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        library.addBook(firstBook);
        library.addBook(secondBook);

        // Act
        library.checkOutBook(1);

        // Assert
        assertThat(secondBook.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowJustAvailableBooks() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        firstBook.setIsCheckOut(true);
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);

        // Act
        String OneBookList = myLibrary.getBookList();

        // Assert
        Integer linesNumber = OneBookList.split("\n").length;
        assertThat(linesNumber, is(1));
    }

    @Test
    /**
     * El nombre de la prueba no es decriptivo frente a lo que esta probando
     */
    public void shouldSetAsUnreserveABook() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book");
        Book secondBook = new Book("Second Book");
        library.addBook(firstBook);
        library.addBook(secondBook);

        // Act
        library.checkOutBook(1);
        library.checkInBook(1);


        // Assert
        assertThat(secondBook.getIsCheckOut(), is(false));

    }

    @Test
    /**
     * Deberían llamarse missingBooks o reservedBooks?
     */
    public void shouldReturnTheListOfMissingBooks() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book");
        myLibrary.addBook(firstBook);
        Book secondBook = new Book("Second Book");
        myLibrary.addBook(secondBook);
        Book thirdBook = new Book("Third Book");
        myLibrary.addBook(thirdBook);

        // Act
        myLibrary.checkOutBook(0);
        myLibrary.checkOutBook(1);
        String OneBookList = myLibrary.getBookList(false);

        // Assert
        Integer linesNumber = OneBookList.split("\n").length;
        assertThat(linesNumber, is(2));
    }

}


