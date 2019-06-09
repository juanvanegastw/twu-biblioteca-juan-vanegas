package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.rent.RentItemException;
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

    private Menu menu;

    @Before
    public void setUp(){
        System.setOut(new PrintStream(this.byteArrayOutputStream));
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
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
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
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String totalMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") +
                this.rentItemService.getItemList(true) + Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        menu.startUserInteraction();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

    @Test
    public void shouldShowLeavingMessageWhenInsertingqonConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String totalMessage = Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act

        when(bufferedReader.readLine()).thenReturn("q");
        this.menu.startUserInteraction();

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

        this.menu.startUserInteraction();

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
        this.menu.startUserInteraction();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), is(totalMessage));

    }

    /*
    * CHECKING OUT BOOKS
    * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectinOption3() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") + this.rentItemService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "book");

        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }


    @Test
    public void shouldCheckOutBookWhenSelecting3AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting3AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") + this.rentItemService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "book")  + String.format(MenuService.checkoutSuccessfullyMessage, "book");


        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenAnIndexOutOfArray() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("1").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") + this.rentItemService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "book") + String.format(MenuService.checkoutUnsuccessfullyMessage, "book");


        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenACharacterNotParseToInt() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("x").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book")+ this.rentItemService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "book") + String.format(MenuService.checkoutUnsuccessfullyMessage, "book");


        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    /*
    * CHECKING IN
    *
    * */
    @Test
    public void shouldShowCheckInMenuWhenSelectinOption4() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentItemService, this.rentMovieService,bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.missingItemsMessage, "book") + this.rentItemService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "book");

        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldCheckOutBookWhenSelecting3then0then4AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("4").thenReturn("0").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThen0() throws IOException, RentItemException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("0").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.rentItemService.checkOutItem(0);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.missingItemsMessage, "book") + this.rentItemService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInSuccessfullyMessage, "book");


        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenANotMissingBook() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("0").thenReturn("q");

        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.missingItemsMessage, "book") + this.rentItemService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInUnsuccessfullyMessage, "book");

        this.menu.startUserInteraction();

        // Verifyin
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenABookOutOfIndex() throws IOException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("1").thenReturn("q");
        Book firstBook = new Book("First Book", 1992, "First Writer");
        this.rentItemService.addItem(firstBook);
        this.menu = new Menu(this.rentItemService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.missingItemsMessage, "book") + this.rentItemService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "book") + String.format(MenuService.checkInUnsuccessfullyMessage, "book");

        // Act
        this.menu.startUserInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }
}
