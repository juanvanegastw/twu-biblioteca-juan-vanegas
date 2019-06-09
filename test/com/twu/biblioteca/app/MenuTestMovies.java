package com.twu.biblioteca.app;

import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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

    @Before
    public void setUp(){
        System.setOut(new PrintStream(this.byteArrayOutputStream));
        this.bufferedReader = mock(BufferedReader.class);
    }

    @After
    public void cleaning() {
        System.setOut(System.out);
    }

    @Test
    public void shouldShowMovieListWhenInserting1onConsole() throws IOException {
        // Arrange
        when(this.bufferedReader.readLine()).thenReturn("2").thenReturn("q");
        Movie movie = new Movie("I Robot", 2016, "Director", 5);
        this.rentMovieService.addItem(movie);
        this.menu = new Menu(this.rentBookService, this.rentMovieService, this.bufferedReader);
        String totalMessage = Menu.generalMenu + String.format(MenuService.availableItemsMessage, "book") +
                this.rentMovieService.getItemList(true) + Menu.generalMenu + Menu.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        menu.startUserInteraction();

        // Verifying
        assertThat( this.byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

    }

}
