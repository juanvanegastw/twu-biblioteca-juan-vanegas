package com.twu.biblioteca.app;
import java.io.IOException;


public class BibliotecaApp {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu(DataBuilder.generateBooksRentService(), DataBuilder.generateMoviesRentService(), null);
        menu.showWelcomeMessage();
        menu.startUserInteraction();
    }
}
