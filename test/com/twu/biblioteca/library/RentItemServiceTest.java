package com.twu.biblioteca.library;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.rent.RentItemService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RentItemServiceTest {

    RentItem firstBook;
    RentItem secondBook ;
    RentItem thirdBook ;

    @Before
    public void setUp(){
        this.firstBook = new RentItem(new Book("First Book", 1952, "First Writer"));
        this.secondBook = new RentItem(new Book("Second Book", 1952, "First Writer"));
        this.thirdBook = new RentItem( new Book("Third Book", 1952, "First Writer"));
    }
    @Test
    public void shouldAddABook(){
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");

        // Act
        boolean bookAdded = myRentItemService.addItem(this.firstBook);

        // Assert
        assertThat(bookAdded, is(true));
    }

    @Test
    public void shouldReturnAStringListWhenBookAdded(){
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        myRentItemService.addItem(this.firstBook);

        // Act
        String OneBookList = myRentItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
    }

    @Test
    public void shouldReturnAStringListWhenTwoBooksAdded(){
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        myRentItemService.addItem(this.firstBook);
        myRentItemService.addItem(this.secondBook);

        // Act
        String OneBookList = myRentItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));
    }

    @Test
    public void shouldPrintBookIndexInfo(){
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        rentItemService.addItem(this.firstBook);
        rentItemService.addItem(this.secondBook);

        // Act
        String bookList = rentItemService.getItemList(true);

        // Assert
        String [] bookListArray = bookList.split("\n");
        int index = 0;
        for (String line: bookListArray){
            String[] lineFields = line.split(RentItem.infoSeparator);
            String firstItem = lineFields[0];
            assertThat(firstItem, is(String.valueOf(index)));
            index++;
        }
    }

    @Test
    public void shouldSetAsReservedABookWhenCheckingItOut() throws RentItemException {
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        rentItemService.addItem(this.firstBook);
        rentItemService.addItem(this.secondBook);

        // Act
        rentItemService.checkOutItem(1);

        // Assert
        assertThat(this.secondBook.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowJustAvailableBooks() throws RentItemException {
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");

        //Act

        this.firstBook.setIsCheckOut(true);

        // Assert
        myRentItemService.addItem(this.firstBook);
        myRentItemService.addItem(this.secondBook);
        String OneBookList = myRentItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));

    }

    @Test
    public void shouldSetAsNotReservedBookWhenCheckingItOutAndReturningIt() throws RentItemException {
        // Arrange
        RentItemService rentItemService = new RentItemService("Biblioteca");
        rentItemService.addItem(this.firstBook);
        rentItemService.addItem(this.secondBook);

        // Act
        rentItemService.checkOutItem(1);
        rentItemService.checkInItem(1);

        // Assert
        assertThat(this.secondBook.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldReturnTheListOfReservedBooks() throws RentItemException {
        // Arrange
        RentItemService myRentItemService = new RentItemService("My RentItemService");
        myRentItemService.addItem(this.firstBook);
        myRentItemService.addItem(this.secondBook);
        myRentItemService.addItem(this.thirdBook);

        // Act
        myRentItemService.checkOutItem(0);
        myRentItemService.checkOutItem(1);
        String OneBookList = myRentItemService.getItemList(false);

        // Assert
        assertThat(OneBookList, is("0,First Book,First Writer,1952\n" +
                "1,Second Book,First Writer,1952\n"));
    }

}
