package com.twu.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Separar responsabilidades de la clase BibliotecaApp (hace muchas cosas, ej. crear la clase menú)
 * Remover imports innecesarios.
 * Definir los modificadores de acceso de todos los métodos.
 */
public class BibliotecaApp {

    static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    static String availableBooksMessage = "Here you can see a list of the books\n\n";
    static String missingBooksMessage = "Here you can see a list of the missing books\n\n";
    static String generalMenu = "\nSelect an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to quit \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert \"4\" to return a book \n" +
            "Insert and press Enter:\n";
    static String leavingMessage = "Thanks for using Biblioteca";
    static String invalidOptionMessage = "Please select a valid option!\n";
    static String selectBookMessage = "\nPlease select the index of the book you want to Check Out\n";
    static String selectBookToReturnMessage = "\n Please select the index of the book you want to Return\n";
    static String checkoutSuccessfullyMessage = "Thank you! Enjoy the book\n";
    static String checkoutUnsuccessfullyMessage = "Sorry, that book is not available\n";
    static String checkInSuccessfullyMessage = "Thank you for returning the book\n";
    static String checkInUnsuccessfullyMessage = "That is not a valid book to return\n";
    private BufferedReader bufferedReader;

    /**
     * Sería más claro si este objeto se llama library ya que todo el codigo esta en inglés
     */
    Library biblioteca = new Library("Biblioteca");

    /**
     * Este constructor sólo se usa en las pruebas, removerlo ya que es innecesario
     */
    BibliotecaApp ( BufferedReader reader){
        this.bufferedReader = reader;
    }

    BibliotecaApp (){
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Separar lógica del main a una clase Main
     * Recibir bibilioteca en el constructor de Biblioteca
     */
    public static void main(String[] args) throws IOException {
        BibliotecaApp myApp = new BibliotecaApp();
        //Mover la carga de datos a otra clase
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

    void showSelectBookToReturnMessage() {System.out.print(selectBookToReturnMessage);}

    /**
     * ListBooks limita a que el método siempre devuelva una lista, qué pasa cuando cambio el tipo de dato
     */
    void listBooks(boolean justAvailable){
        String bookList = biblioteca.getBookList(justAvailable);
        if (justAvailable) {
            System.out.print(availableBooksMessage);
        }
        else{
            System.out.print(missingBooksMessage);
        }
        System.out.print(bookList);
    }

    void showGeneralMenu(){
        System.out.print(generalMenu);
    }

    private void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    /**
     * El nombre de este método no es lo sificientemente descriptivo
     */
    boolean parseOption(String option) throws IOException{
        boolean programMustContinue = true;
        switch (option){
            case "1":
                listBooks(true);
                break;
            case "2":
                showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                listBooks(true);
                showSelectBookMessage();
                userCheckingOutBook();
                break;
            case "4":
                listBooks(false);
                showSelectBookToReturnMessage();
                userCheckInBook();
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    private void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    private String readOptionSelected() throws IOException{
        return this.bufferedReader.readLine();
    }

    /**
     * Los nombres de los métodos empiezan con una acción.
     */
    void userInteraction() throws IOException{
        showGeneralMenu();
        boolean programMustContinue = parseOption(readOptionSelected());
        if(programMustContinue)
            userInteraction();
    }

    /**
     * ¿Qué pasa cuando recibes un Exception?
     * Los nombres de los métodos empiezan con una acción.
     */
    void userCheckingOutBook() throws IOException{
        String bookIndex = readOptionSelected();
        try {
            int index = Integer.valueOf(bookIndex);
            this.biblioteca.checkOutBook(index);
            showCheckoutSuccesfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException| BookAlreadyCheckedOutException | BookAlreadyCheckedInException exception) {
            showCheckoutUnsuccesfullyMessage();
        }
    }

    /**
     * Los nombres de los métodos empiezan con una acción.
     * ¿Qué pasa cuando recibes un Exception?
     */
    void userCheckInBook() throws IOException{
        String bookIndex = readOptionSelected();
        try {
            int index = Integer.valueOf(bookIndex);
            this.biblioteca.checkInBook(index);
            showCheckInSuccesfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException | BookAlreadyCheckedOutException | BookAlreadyCheckedInException exception){
            showCheckInUnsuccesfullyMessage();
        }
    }

    void showCheckoutSuccesfullyMessage(){
        System.out.print(checkoutSuccessfullyMessage);
    }

    void showCheckoutUnsuccesfullyMessage(){
        System.out.print(checkoutUnsuccessfullyMessage);
    }

    void showCheckInSuccesfullyMessage(){
        System.out.print(checkInSuccessfullyMessage);
    }

    void showCheckInUnsuccesfullyMessage(){
        System.out.print(checkInUnsuccessfullyMessage);
    }
}
