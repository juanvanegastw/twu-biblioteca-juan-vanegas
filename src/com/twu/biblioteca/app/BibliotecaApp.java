package com.twu.biblioteca.app;
import com.twu.biblioteca.library.user.UserException;

public class BibliotecaApp {
    public static void main(String[] args) throws UserException {
        Menu menu = new Menu(DataBuilder.generateBooksRentService(), DataBuilder.generateMoviesRentService(), null);
        LogIn logIn = DataBuilder.generateLogIn();
        menu.setLogIn(logIn);
        menu.startLibrary();
    }
}
