package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    static String availableBooksMessage = "Here you can see a list of the books\n\n";
    static String generalMenu = "Select an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to quit \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert and press Enter:";
    static String leavingMessage = "Thanks for using Biblioteca";
    static String invalidOptionMessage = "Please select a valid option!\n";
    static String selectBookMessage = "Please select the index of the book you want to Check Out\n";
    private BufferedReader bufferedReader;

    Library biblioteca = new Library("Biblioteca");

    BibliotecaApp ( BufferedReader reader){
        this.bufferedReader = reader;
    }

    BibliotecaApp (){
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws IOException {
        BibliotecaApp myApp = new BibliotecaApp();
        myApp.biblioteca.addBook(new Book("First Book", 1992, "First Writer"));
        myApp.biblioteca.addBook(new Book("Second Book", 2019, "Second Writer"));
        myApp.biblioteca.addBook(new Book("Third Book", 2010, "Third Writer"));

        myApp.showWelcomeMessage();
        myApp.userInteraction();
    }

    void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    void showSelectBookMessage() {System.out.print(selectBookMessage);}

    void listBooks(){
        String bookList = biblioteca.getBookList();
        System.out.print(availableBooksMessage);
        System.out.print(bookList);
    }

    void showGeneralMenu(){
        System.out.print(generalMenu);
    }

    private void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    boolean parseOption(String option) throws IOException{
        boolean programMustContinue = true;
        switch (option){
            case "1":
                listBooks();
                break;
            case "2":
                showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                listBooks();
                showSelectBookMessage();
                userCheckingOutBook();
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    private void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    private String  readOptionSelected() throws IOException{
        return this.bufferedReader.readLine();
    }

    void userInteraction() throws IOException {
        showGeneralMenu();
        boolean programMustContinue = parseOption(readOptionSelected());
        if(programMustContinue)
            userInteraction();
    }

    void userCheckingOutBook() throws IOException{
        String bookIndex = readOptionSelected();
        try {
            int index = Integer.valueOf(bookIndex);
            this.biblioteca.checkOutBook(index);
        }
        catch (NumberFormatException numberformatException)
        {
            System.out.print("Error");

        }

    }
}
