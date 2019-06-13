package com.twu.biblioteca.app;

import com.twu.biblioteca.library.item.RentalItem;
import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.item.RentalItemException;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuTestMovies {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private RentalItemService rentMovieService = new RentalItemService("Movies");
    private Menu menu;
    private BufferedReader bufferedReader;
    private RentalItemService rentBookService = DataBuilder.generateBooksRentService();
    private RentalItem firstMovie;
    private LibraryUser user;

    @Before
    public void setUp() throws UserException {
        System.setOut(new PrintStream(this.byteArrayOutputStream));
        this.bufferedReader = mock(BufferedReader.class);
        this.firstMovie = new RentalItem(new Movie("I Robot", 2016, "Director", 5));
        this.user = new LibraryUser("111-1111", "password", "name", "email", "094");
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
        String message = this.rentMovieService.getItemList(true);

        // Act
        menu.startLibrary();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString(), containsString(message));

    }

    /*
     * CHECKING OUT BOOKS
     * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectingOption5() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentBookService, this.rentMovieService,bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemMessage, "movie");

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(this.byteArrayOutputStream.toString(), containsString(message));

    }


    @Test
    public void shouldCheckOutMovieWhenSelecting5AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertTrue(this.firstMovie.getIsCheckOut());

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting5AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemMessage, "movie")  + String.format(MenuServiceMessages.checkoutSuccessfullyMessage, "movie");

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting5AndThenAnIndexOutOfArray() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("1").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemMessage, "movie") + String.format(MenuServiceMessages.checkoutUnsuccessfullyMessage, "movie");


        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting5AndThenACharacterNotParseToInt() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("x").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemMessage, "movie") + String.format(MenuServiceMessages.checkoutUnsuccessfullyMessage, "movie");

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    /*
     * CHECKING IN
     *
     * */
    @Test
    public void shouldShowCheckInMenuWhenSelectingOption6() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("x").thenReturn("q");
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemToReturnMessage, "movie");

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    @Test
    public void shouldCheckOutBookWhenSelecting5then0then6AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("5").thenReturn("0").thenReturn("6").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentMovieService,this.rentMovieService, bufferedReader);

        // Act
        this.menu.startLibrary();

        // Verifying
        assertFalse(this.firstMovie.getIsCheckOut());
    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThen0() throws IOException, RentalItemException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("0").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.rentMovieService.checkOutItem(0, user);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemToReturnMessage, "movie") + String.format(MenuServiceMessages.checkInSuccessfullyMessage, "movie");


        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThenANotReservedBook() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("0").thenReturn("q");

        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemToReturnMessage, "movie") + String.format(MenuServiceMessages.checkInUnsuccessfullyMessage, "movie");

        this.menu.startLibrary();

        // Verifyin
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting6AndThenABookOutOfIndex() throws IOException {
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("6").thenReturn("1").thenReturn("q");
        this.rentMovieService.addItem(this.firstMovie);
        this.menu = new Menu(this.rentBookService,this.rentMovieService, bufferedReader);
        String message = String.format(MenuServiceMessages.selectItemToReturnMessage, "movie") + String.format(MenuServiceMessages.checkInUnsuccessfullyMessage, "movie");

        // Act
        this.menu.startLibrary();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(message));

    }
}
