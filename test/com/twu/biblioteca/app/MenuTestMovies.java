package com.twu.biblioteca.app;

import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.rent.RentItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuTestMovies {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private RentItemService rentMovieService = new RentItemService("Movies");
    private Menu menu;
    private BufferedReader bufferedReader;
    private RentItemService rentBookService = DataBuilder.generateBooksRentService();
    private RentItem firstMovie;
    @Before
    public void setUp(){
        System.setOut(new PrintStream(this.byteArrayOutputStream));
        this.bufferedReader = mock(BufferedReader.class);
        this.firstMovie = new RentItem(new Movie("I Robot", 2016, "Director", 5));
    }

    @After
    public void cleaning() {
        System.setOut(System.out);
    }

    @Test
    public void shouldShowMovieListWhenInserting2onConsole() throws IOException {
        // Arrange
        when(this.bufferedReader.readLine()).thenReturn("2").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService, this.rentMovieService, this.bufferedReader);
        String totalMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "movie") +
                this.rentMovieService.getItemList(true) + Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        menu.startMenuServices();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

    /*
     * CHECKING OUT BOOKS
     * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectinOption5() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentBookService, this.rentMovieService,bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "movie") + this.rentMovieService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "movie");

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }


    @Test
    public void shouldCheckOutMovieWhenSelecting5AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.firstMovie.getIsCheckOut(), is(true));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting5AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "movie") + this.rentMovieService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "movie")  + String.format(MenuService.checkoutSuccessfullyMessage, "movie");

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting5AndThenAnIndexOutOfArray() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("1").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "movie") + this.rentMovieService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "movie") + String.format(MenuService.checkoutUnsuccessfullyMessage, "movie");


        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting5AndThenACharacterNotParseToInt() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("x").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "movie")+ this.rentMovieService.getItemList(true);
        String secondMessage = String.format(MenuService.selectItemMessage, "movie") + String.format(MenuService.checkoutUnsuccessfullyMessage, "movie");

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    /*
     * CHECKING IN
     *
     * */
    @Test
    public void shouldShowCheckInMenuWhenSelectinOption6() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.reservedItemsMessage, "movie") + this.rentMovieService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "movie");

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldCheckOutBookWhenSelecting5then0then6AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("6").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(this.firstMovie.getIsCheckOut(), is(false));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThen0() throws IOException, RentItemException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.rentMovieService.checkOutItem(0);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.reservedItemsMessage, "movie") + this.rentMovieService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "movie") + String.format(MenuService.checkInSuccessfullyMessage, "movie");


        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThenANotReservedBook() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("0").thenReturn("q");

        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.reservedItemsMessage, "movie") + this.rentMovieService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "movie") + String.format(MenuService.checkInUnsuccessfullyMessage, "movie");

        this.menu.startMenuServices();

        // Verifyin
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThenABookOutOfIndex() throws IOException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("1").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String firstMessage = Menu.generalMenu + String.format(MenuService.reservedItemsMessage, "movie") + this.rentMovieService.getItemList(false);
        String secondMessage = String.format(MenuService.selectItemToReturnMessage, "movie") + String.format(MenuService.checkInUnsuccessfullyMessage, "movie");

        // Act
        this.menu.startMenuServices();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

    }
}
