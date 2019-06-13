package com.twu.biblioteca.library.item;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.item.RentalItem;
import com.twu.biblioteca.library.item.RentalItemException;
import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RentalItemServiceTest {

    RentalItem firstBook;
    RentalItem secondBook ;
    RentalItem thirdBook ;
    LibraryUser user;

    @Before
    public void setUp() throws UserException {
        this.firstBook = new RentalItem(new Book("First Book", 1952, "First Writer"));
        this.secondBook = new RentalItem(new Book("Second Book", 1952, "First Writer"));
        this.thirdBook = new RentalItem( new Book("Third Book", 1952, "First Writer"));
        this.user = new LibraryUser("111-1111", "password", "name", "email", "094");
    }

    @Test
    public void shouldAddABook(){
        // Arrange
        RentalItemService myRentalItemService = new RentalItemService("My RentalItemService");

        // Act
        boolean bookAdded = myRentalItemService.addItem(this.firstBook);

        // Assert
        assertThat(bookAdded, is(true));
    }

    @Test
    public void shouldReturnAStringListWhenBookAdded(){
        // Arrange
        RentalItemService myRentalItemService = new RentalItemService("My RentalItemService");
        myRentalItemService.addItem(this.firstBook);

        // Act
        String OneBookList = myRentalItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
    }

    @Test
    public void shouldReturnAStringListWhenTwoBooksAdded(){
        // Arrange
        RentalItemService myRentalItemService = new RentalItemService("My RentalItemService");
        myRentalItemService.addItem(this.firstBook);
        myRentalItemService.addItem(this.secondBook);

        // Act
        String OneBookList = myRentalItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("0,First Book,First Writer,1952"));
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));
    }

    @Test
    /**
     * Aquí puedes encontrar algunos ejemplos de Comparator con asserts
     * https://stackoverflow.com/questions/17949752/junit-matcher-for-comparators
     */
    public void shouldPrintBookIndexInfo(){
        // Arrange
        RentalItemService rentalItemService = new RentalItemService("Biblioteca");
        rentalItemService.addItem(this.firstBook);
        rentalItemService.addItem(this.secondBook);

        // Act
        String bookList = rentalItemService.getItemList(true);

        // Assert
        String [] bookListArray = bookList.split("\n");
        int index = 0;
        for (String line: bookListArray){
            String[] lineFields = line.split(RentalItem.infoSeparator);
            String firstItem = lineFields[0];
            assertThat(firstItem, is(String.valueOf(index)));
            index++;
        }
    }

    @Test
    public void shouldSetAsReservedABookWhenCheckingItOut() throws RentalItemException {
        // Arrange
        RentalItemService rentalItemService = new RentalItemService("Biblioteca");
        rentalItemService.addItem(this.firstBook);
        rentalItemService.addItem(this.secondBook);

        // Act
        rentalItemService.checkOutItem(1, user);

        // Assert
        assertThat(this.secondBook.getIsCheckOut(), is(true));
    }

    @Test
    public void shouldShowJustAvailableBooks() throws RentalItemException {
        // Arrange
        RentalItemService myRentalItemService = new RentalItemService("My RentalItemService");

        //Act

        this.firstBook.setIsCheckOut(true, user);

        // Assert
        myRentalItemService.addItem(this.firstBook);
        myRentalItemService.addItem(this.secondBook);
        String OneBookList = myRentalItemService.getItemList(true);

        // Assert
        assertThat(OneBookList, containsString("1,Second Book,First Writer,1952"));
    }

    @Test
    public void shouldSetAsNotReservedBookWhenCheckingItOutAndReturningIt() throws RentalItemException {
        // Arrange
        RentalItemService rentalItemService = new RentalItemService("Biblioteca");
        rentalItemService.addItem(this.firstBook);
        rentalItemService.addItem(this.secondBook);

        // Act
        rentalItemService.checkOutItem(1, user);
        rentalItemService.checkInItem(1);

        // Assert
        assertThat(this.secondBook.getIsCheckOut(), is(false));
    }

    @Test
    public void shouldReturnTheListOfReservedBooks() throws RentalItemException {
        // Arrange
        RentalItemService myRentalItemService = new RentalItemService("My RentalItemService");
        myRentalItemService.addItem(this.firstBook);
        myRentalItemService.addItem(this.secondBook);
        myRentalItemService.addItem(this.thirdBook);

        // Act
        myRentalItemService.checkOutItem(0, user);
        myRentalItemService.checkOutItem(1, user);
        String OneBookList = myRentalItemService.getItemList(false);

        // Assert
        assertThat(OneBookList,
                allOf(
                        containsString("0,First Book,First Writer,1952,Reserved by"),
                        containsString("1,Second Book,First Writer,1952,Reserved by")
                ));
    }
}
