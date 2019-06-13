package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.item.RentalItem;
import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.item.RentalItemException;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * En general para todas las pruebas es importante mantener el mismo estándar de nombrar los nombres de las pruebas, p.ej si vas a usar should todas deberían hacerlo, así como los guiones _
 */
public class MenuTest {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private RentalItemService rentalItemService = new RentalItemService("Biblioteca");
    private RentalItemService rentMovieService = DataBuilder.generateMoviesRentService();
    private RentalItem firstBook;
    private LibraryUser user;
    private Menu menu;

    @Before
    public void setUp() throws UserException{
        System.setOut(new PrintStream(this.byteArrayOutputStream));
        this.firstBook = new RentalItem(new Book("First Book", 1992, "First Writer"));
        this.user = new LibraryUser("111-1111", "password", "name", "email", "094");
    }

    @After
    public void cleaning(){
        System.setOut(System.out);
    }


    @Test
    public void shouldShowWelcomeMessageWhenStarting() throws IOException {
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("q");
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String message = MenuMessages.welcomeMessage;

        // Act
        menu.startLibrary();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), containsString(message));
    }
    @Test
    public void shouldShowTheBookListWhenSelectingOptionOne(){
        // Arrange
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, null);
        String totalMessage = String.format(MenuServiceMessages.availableItemsMessage, "book") + this.rentalItemService.getItemList(true);
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        this.menu.executeUserSelectedOption("1");

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));
    }

    @Test
    public void shouldShowLeavingMessageWhenSelectingTheLetterQ(){
        // Arrange
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,null);

        // Act
        this.menu.executeUserSelectedOption("q");

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), is(MenuMessages.leavingMessage));
    }


    @Test
    public void shouldShowBookListWhenInserting1onConsole() throws IOException {
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.availableItemsMessage, "book") + this.rentalItemService.getItemList(true);

        // Act
        menu.startLibrary();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), containsString(message));
    }

    @Test
    public void shouldShowLeavingMessageWhenInsertingOnConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,bufferedReader);
        String message = MenuMessages.leavingMessage;

        // Act
        when(bufferedReader.readLine()).thenReturn("q");
        this.menu.startLibrary();

        // Verifying
        assertThat( byteArrayOutputStream.toString(), containsString(message));
    }

    @Test
    public void shouldShowErrorMessageWhenInsertingANotRecognizedOptionOnConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Another").thenReturn("q");
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,bufferedReader);
        String message = MenuMessages.invalidOptionMessage;

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), containsString(message));
    }

    @Test
    public void shouldShowBookListAndThenCloseWhenSelecting1andthenq() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,bufferedReader);

        String message = String.format(MenuServiceMessages.availableItemsMessage, "book") + this.rentalItemService.getItemList(true);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), containsString(message));
    }

    /*
    * CHECKING OUT BOOKS
    * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectinOption3() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), containsString(expected));
    }

    @Test
    public void shouldCheckOutBookWhenSelecting3AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertTrue(this.firstBook.getIsCheckOut());

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting3AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemMessage, "book")  + String.format(MenuServiceMessages.checkoutSuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));
    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenAnIndexOutOfArray() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("1").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemMessage, "book") + String.format(MenuServiceMessages.checkoutUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));
    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenACharacterNotParseToInt() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("x").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemMessage, "book") + String.format(MenuServiceMessages.checkoutUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected ));

    }

    /*
    * CHECKING IN
    *
    * */
    @Test
    public void shouldShowCheckInMenuWhenSelectinOption4() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentalItemService, this.rentMovieService,bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemToReturnMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);

        this.menu.setLogIn(logIn);
        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldCheckOutBookWhenSelecting3then0then4AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("4").thenReturn("0").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertFalse(this.firstBook.getIsCheckOut());
    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThen0() throws IOException, RentalItemException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.rentalItemService.checkOutItem(0, user);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemToReturnMessage, "book") + String.format(MenuServiceMessages.checkInSuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenANotReservedBook() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");

        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemToReturnMessage, "book") + String.format(MenuServiceMessages.checkInUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenABookOutOfIndex() throws IOException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("1").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuServiceMessages.selectItemToReturnMessage, "book") + String.format(MenuServiceMessages.checkInUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulLoginWhenInsertingCorrectPassword() throws IOException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("111-1111").thenReturn("1").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);


        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(MenuMessages.welcomeMessage));

    }

    @Test
    public void shouldShowUserInfoWhenLoginIntoTheApp() throws IOException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("p").thenReturn("q");
        this.rentalItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentalItemService,this.rentMovieService, bufferedReader);
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        this.menu.setLogIn(logIn);


        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(MenuMessages.userInfoMessage));

    }


}
