package com.twu.biblioteca.app;
import java.io.IOException;


public class BibliotecaApp {
    public static void main(String[] args) throws IOException {
        DataBuilder dataBuilder = new DataBuilder();
        Menu menu = new Menu(dataBuilder.generateLibrary(), null);
        menu.showWelcomeMessage();
        menu.startUserInteraction();
    }
}
