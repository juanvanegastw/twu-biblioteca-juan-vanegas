package com.twu.biblioteca;

import java.io.InputStream;
import java.util.Scanner;

public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    static String availableBooksMessage = "Here you can see a list of the books\n\n";
    static String generalMenu = "Select an option\nInsert \"1\" to show book list\nInsert \"2\" to quit\nInsert and press Enter:";
    static String leavingMessage = "Thanks for using Biblioteca";

    Library biblioteca = new Library("Biblioteca");

    public static void main(String[] args) {
        BibliotecaApp myApp = new BibliotecaApp();
        myApp.biblioteca.addBook(new Book("First Book", 1992, "First Writer"));
        myApp.biblioteca.addBook(new Book("Second Book", 2019, "Second Writer"));
        myApp.biblioteca.addBook(new Book("Third Book", 2010, "Third Writer"));

        myApp.showWelcomeMessage();
        myApp.userInteraction(System.in);
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

    void parseOption(String option){
        if (option.equals("1"))
            listBooks();
        else
            showLeavingMessage();
    }

    void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    void userInteraction(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        showGeneralMenu();
        String option = scanner.nextLine();
        parseOption(option);
    }
}
