package com.twu.biblioteca.library;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.rent.RentItemService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RentItemServiceTest {

    @Test
    public void shouldAddABook(){
        // Arrange
        Book firstBook = new Book("First Book", 1952, "First Writer");
        RentItemService myRentItemService = new RentItemService("My RentItemService");

        // Act
        boolean bookAdded = myRentItemService.addItem(firstBook);

        // Assert
        assertThat(bookAdded, is(true));
    }

    @Test
    public void shouldReturnAStringListWhenBookAdded(){
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        myRentItemService.addItem(firstBook);

        // Act
        String OneBookList = myRentItemService.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
    }

    @Test
    public void shouldReturnAStringListWhenTwoBooksAdded(){
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        myRentItemService.addItem(firstBook);
        myRentItemService.addItem(secondBook);

        // Act
        String OneBookList = myRentItemService.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));
    }

    @Test
    public void shouldPrintBookIndexInfo(){
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        rentItemService.addItem(firstBook);
        rentItemService.addItem(secondBook);

        // Act
        String bookList = rentItemService.getBookList(true);

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
    public void shouldSetAsReservedABookWhenCheckingItOut() throws RentItemException {
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        rentItemService.addItem(firstBook);
        rentItemService.addItem(secondBook);

        // Act
        rentItemService.checkOutBook(1);

        // Assert
        assertThat(secondBook.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowJustAvailableBooks() throws RentItemException {
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");

        //Act

        firstBook.setIsCheckOut(true);

        // Assert
        myRentItemService.addItem(firstBook);
        myRentItemService.addItem(secondBook);
        String OneBookList = myRentItemService.getBookList(true);

        // Assert
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));

    }

    @Test
    public void shouldSetAsNotReservedBookWhenCheckingItOutAndReturningIt() throws RentItemException {
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        rentItemService.addItem(firstBook);
        rentItemService.addItem(secondBook);

        // Act
        rentItemService.checkOutBook(1);
        rentItemService.checkInBook(1);

        // Assert
        assertThat(secondBook.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldReturnTheListOfReservedBooks() throws RentItemException {
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        Book firstBook = new Book("First Book", 1952, "First Writer");
        myRentItemService.addItem(firstBook);
        Book secondBook = new Book("Second Book", 1952, "First Writer");
        myRentItemService.addItem(secondBook);
        Book thirdBook = new Book("Third Book", 1952, "First Writer");
        myRentItemService.addItem(thirdBook);

        // Act
        myRentItemService.checkOutBook(0);
        myRentItemService.checkOutBook(1);
        String OneBookList = myRentItemService.getBookList(false);

        // Assert
        assertThat(OneBookList, is("0,First Book,First Writer,1952\n" +
                "1,Second Book,First Writer,1952\n"));
    }

}
