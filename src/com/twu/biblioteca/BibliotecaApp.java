package com.twu.biblioteca;

public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n";

    public static void main(String[] args) {
        BibliotecaApp myApp = new BibliotecaApp();
        myApp.showWelcomeMessage();
    }

    void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

}
