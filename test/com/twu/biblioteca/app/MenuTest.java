package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuTest {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private RentItemService rentItemService = new RentItemService("Biblioteca");
    private RentItemService rentMovieService = DataBuilder.generateMoviesRentService();
    private RentItem firstBook;
    private LibraryUser user;
    private Menu menu;

    @Before
    public void setUp() throws UserException{
        System.setOut(new PrintStream(this.byteArrayOutputStream));
        this.firstBook = new RentItem(new Book("First Book", 1992, "First Writer"));
        this.user = new LibraryUser("111-1111", "password");


    }

    @After
    public void cleaning(){
        System.setOut(System.out);
    }

    @Test
    public void testWelcomeMessageNotNull(){
        //Setting
        this.menu = new Menu(this.rentItemService, this.rentMovieService,null);


        //Executing
        this.menu.showWelcomeMessage();

        //Verifying
        Assert.assertTrue(this.byteArrayOutputStream.toString().length() > 0);

    }

    @Test
    public void testWelcomeMessageCorrectMessage(){
        //Setting
        this.menu = new Menu(this.rentItemService, this.rentMovieService,null);


        //Executing
        this.menu.showWelcomeMessage();

        //Verifying
        assertThat(this.byteArrayOutputStream.toString(), is(Menu.welcomeMessage));

    }



    @Test
    public void shouldShowTheGeneralMenu(){
        // Arrange
        this.menu = new Menu(this.rentItemService,this.rentMovieService, null);

        // Act
        this.menu.showGeneralMenu();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), is(Menu.generalMenu));

    }

    @Test
    public void shouldShowTheBookListWhenSelecting1(){
        // Arrange
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, null);
        String totalMessage = String.format(MenuService.availableItemsMessage, "book") + this.rentItemService.getItemList(true);
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        this.menu.executeUserSelectedOption("1");

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

    @Test
    public void shouldShowLeavingMessageWhenSelectingq(){
        // Arrange
        this.menu = new Menu(this.rentItemService, this.rentMovieService,null);

        // Act
        this.menu.executeUserSelectedOption("q");

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), is(Menu.leavingMessage));

    }

    @Test
    public void shouldShowBookListWhenInserting1onConsole() throws IOException {
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String totalMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") +
                this.rentItemService.getItemList(true) + Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        menu.startMenuServices();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

    @Test
    public void shouldShowLeavingMessageWhenInsertingqonConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String totalMessage = Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act

        when(bufferedReader.readLine()).thenReturn("q");
        this.menu.startMenuServices();

        // Verifying
        assertThat( byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

    @Test
    public void shouldShowErrorMessageWhenInsertingANotRecognizedOptionOnConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Another").thenReturn("q");
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String totalMessage = Menu.generalMenu + Menu.invalidOptionMessage
                + Menu.generalMenu + Menu.leavingMessage;

        // Act

        this.menu.startMenuServices();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), is(totalMessage));

    }

    @Test
    public void shouldShowBookListAndThenCloseWhenSelecting1andthenq() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);

        String firstlMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") + this.rentItemService.getItemList(true);
        String secondMessage = Menu.generalMenu + Menu.leavingMessage;
        String totalMessage = firstlMessage + secondMessage;

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), is(totalMessage));

    }

    /*
    * CHECKING OUT BOOKS
    * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectinOption3() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String expected = String.format(MenuService.selectItemMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);
        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), containsString(expected));

    }


    @Test
    public void shouldCheckOutBookWhenSelecting3AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);
        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.firstBook.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting3AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemMessage, "book")  + String.format(MenuService.checkoutSuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenAnIndexOutOfArray() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("1").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemMessage, "book") + String.format(MenuService.checkoutUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenACharacterNotParseToInt() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("x").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemMessage, "book") + String.format(MenuService.checkoutUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startMenuServices();

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
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String expected = String.format(MenuService.selectItemToReturnMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);
        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldCheckOutBookWhenSelecting3then0then4AndThen0() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("4").thenReturn("0").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);


        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.firstBook.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThen0() throws IOException, RentItemException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.rentItemService.checkOutItem(0, user);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInSuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenANotReservedBook() throws IOException, UserException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("0").thenReturn("q");

        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);
        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenABookOutOfIndex() throws IOException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("111-1111").thenReturn("1").thenReturn("1").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String expected = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInUnsuccessfullyMessage, "book");
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);
        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(expected));

    }

    @Test
    public void shouldShowSuccessfulLoginWhenInsertingCorrectPassword() throws IOException, UserException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("111-1111").thenReturn("1").thenReturn("q");
        this.rentItemService.addItem(this.firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        LogIn logIn= new LogIn(bufferedReader);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3");
        logIn.addValidUser(thirdLibraryUser);
        this.menu.setLogIn(logIn);


        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(Menu.welcomeMessage));

    }


}
