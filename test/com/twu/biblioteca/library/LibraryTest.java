package com.twu.biblioteca.library;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LibraryTest {

    @Test
    public void shouldAddABook(){
        // Arrange
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Library myLibrary = new Library("My Library");

        // Act
        boolean bookAdded = myLibrary.addBook(firstBook);

        // Assert
        assertThat(bookAdded, is(true));
    }

    @Test
    public void shouldReturnAStringListWhenBookAdded(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        myLibrary.addBook(firstBook);

        // Act
        String OneBookList = myLibrary.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
    }

    @Test
    public void shouldReturnAStringListWhenTwoBooksAdded(){
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);

        // Act
        String OneBookList = myLibrary.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));
    }

    @Test
    public void shouldPrintBookIndexInfo(){
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        library.addBook(firstBook);
        library.addBook(secondBook);

        // Act
        String bookList = library.getBookList(true);

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
    public void shouldSetAsReservedABookWhenCheckingItOut() throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException {
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
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
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");

        //Act

        firstBook.setIsCheckOut(true);

        // Assert
        myLibrary.addBook(firstBook);
        myLibrary.addBook(secondBook);
        String OneBookList = myLibrary.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));

    }

    @Test
    public void shouldSetAsNotReservedBookWhenCheckingItOutAndReturningIt() throws LibraryException{
        // Arrange
        Library library = new Library("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        library.addBook(firstBook);
        library.addBook(secondBook);

        // Act
        library.checkOutBook(1);
        library.checkInBook(1);

        // Assert
        assertThat(secondBook.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldReturnTheListOfReservedBooks() throws LibraryException{
        // Arrange
        Library myLibrary = new Library("My Library");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        myLibrary.addBook(firstBook);
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        myLibrary.addBook(secondBook);
        Book thirdBook = new Book("Third Book", 1952, "First Writer");
        myLibrary.addBook(thirdBook);

        // Act
        myLibrary.checkOutBook(0);
        myLibrary.checkOutBook(1);
        String OneBookList = myLibrary.getBookList(false);

        // Assert
        assertThat(OneBookList, is("0,First Book,First Writer,1952\n" +
                "1,Second Book,First Writer,1952\n"));
    }

}


