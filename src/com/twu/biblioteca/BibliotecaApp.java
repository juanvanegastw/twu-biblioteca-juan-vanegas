package com.twu.biblioteca;

public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    static String availableBooksMessage = "Here you can see a list of the books\n\n";

    Library biblioteca = new Library("Biblioteca");

    public static void main(String[] args) {
        BibliotecaApp myApp = new BibliotecaApp();
        myApp.biblioteca.addBook(new Book("First Book"));
        myApp.biblioteca.addBook(new Book("Second Book"));
        myApp.biblioteca.addBook(new Book("Third Book"));

        myApp.showWelcomeMessage();
        myApp.listBooks();
    }

    void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    void listBooks(){
        String bookList = biblioteca.getBookList();
        System.out.print(availableBooksMessage);
        System.out.print(bookList);
    }
}
