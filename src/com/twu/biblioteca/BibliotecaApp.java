package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    static String availableBooksMessage = "Here you can see a list of the books\n\n";
    static String generalMenu = "Select an option\nInsert \"1\" to show book list\nInsert \"2\" to quit\nInsert and press Enter:";
    static String leavingMessage = "Thanks for using Biblioteca";
    static String invalidOptionMessage = "Please select a valid option!\n";

    Library biblioteca = new Library("Biblioteca");

    public static void main(String[] args) throws IOException {
        BibliotecaApp myApp = new BibliotecaApp();
        myApp.biblioteca.addBook(new Book("First Book", 1992, "First Writer"));
        myApp.biblioteca.addBook(new Book("Second Book", 2019, "Second Writer"));
        myApp.biblioteca.addBook(new Book("Third Book", 2010, "Third Writer"));

        myApp.showWelcomeMessage();
        myApp.userInteraction(new BufferedReader(new InputStreamReader(System.in)));
    }

    void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    void listBooks(){
        String bookList = biblioteca.getBookList();
        System.out.print(availableBooksMessage);
        System.out.print(bookList);
    }

    void showGeneralMenu(){
        System.out.print(generalMenu);
    }

    void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    boolean parseOption(String option){
        boolean programMustContinue = true;
        switch (option){
            case "1":
                listBooks();
                break;
            case "2":
                showLeavingMessage();
                programMustContinue = false;
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    void userInteraction(BufferedReader reader) throws IOException {
        showGeneralMenu();
        String option = reader.readLine();
        boolean programMustContinue = parseOption(option);
        if(programMustContinue)
            userInteraction(reader);
    }
}
