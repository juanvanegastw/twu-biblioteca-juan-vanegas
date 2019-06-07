package com.twu.biblioteca.app;

import com.twu.biblioteca.library.Library;
import com.twu.biblioteca.library.LibraryException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Menu {

    private BufferedReader bufferedReader;
    private Library library;

    protected static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    protected static String availableBooksMessage = "Here you can see a list of the books\n\n";
    protected static String missingBooksMessage = "Here you can see a list of the missing books\n\n";
    protected static String generalMenu = "\nSelect an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to quit \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert \"4\" to return a book \n" +
            "Insert and press Enter:\n";
    protected static String leavingMessage = "Thanks for using Biblioteca";
    protected static String invalidOptionMessage = "Please select a valid option!\n";
    protected static String selectBookMessage = "\nPlease select the index of the book you want to Check Out\n";
    protected static String selectBookToReturnMessage = "\n Please select the index of the book you want to Return\n";
    protected static String checkoutSuccessfullyMessage = "Thank you! Enjoy the book\n";
    protected static String checkoutUnsuccessfullyMessage = "Sorry, that book is not available\n";
    protected static String checkInSuccessfullyMessage = "Thank you for returning the book\n";
    protected static String checkInUnsuccessfullyMessage = "That is not a valid book to return\n";
    protected static String generalExceptionMessage = "A error has happened and has thrown the next Exception:\n";


    protected Menu ( Library library, BufferedReader reader){
        this.bufferedReader = (reader==null) ? new BufferedReader(new InputStreamReader(System.in)): reader;
        this.library = library;
    }

    protected void showTheListOfBooks(boolean justAvailable){
        String bookList = this.library.getBookList(justAvailable);
        if (justAvailable) {
            System.out.print(availableBooksMessage);
        }
        else{
            System.out.print(missingBooksMessage);
        }
        System.out.print(bookList);
    }

    protected void showGeneralMenu(){
        System.out.print(generalMenu);
    }

    private void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    protected boolean executeUserSelectedOption(String option){
        boolean programMustContinue = true;
        switch (option){
            case "1":
                showTheListOfBooks(true);
                break;
            case "2":
                showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                showTheListOfBooks(true);
                showSelectBookMessage();
                checkOutABook();
                break;
            case "4":
                showTheListOfBooks(false);
                showSelectBookToReturnMessage();
                checkInABook();
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    private void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    private String readOptionSelected(){
        try {
            return this.bufferedReader.readLine();
        }
        catch (Exception exception){
            showGeneralExceptionMessage(exception);
            return "";
        }
    }

    protected void startUserInteraction(){
        showGeneralMenu();
        boolean programMustContinue = executeUserSelectedOption(readOptionSelected());
        if(programMustContinue)
            startUserInteraction();
    }


    protected void checkOutABook() {
        try {
            String bookIndex = readOptionSelected();
            int index = Integer.valueOf(bookIndex);
            this.library.checkOutBook(index);
            showCheckoutSuccesfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException| LibraryException exception) {
            showCheckoutUnsuccesfullyMessage();
        }
        catch (Exception exception){
            showGeneralExceptionMessage(exception);
        }
    }

    protected void checkInABook(){
        try {
            String bookIndex = readOptionSelected();
            int index = Integer.valueOf(bookIndex);
            this.library.checkInBook(index);
            showCheckInSuccessfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException | LibraryException exception){
            showCheckInUnsuccesfullyMessage();
        }
        catch (Exception exception){
            showGeneralExceptionMessage(exception);
        }
    }

    protected void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    private void showSelectBookMessage() {System.out.print(selectBookMessage);}

    private void showSelectBookToReturnMessage() {System.out.print(selectBookToReturnMessage);}

    private void showCheckoutSuccesfullyMessage(){
        System.out.print(checkoutSuccessfullyMessage);
    }

    private void showCheckoutUnsuccesfullyMessage(){
        System.out.print(checkoutUnsuccessfullyMessage);
    }

    private void showCheckInSuccessfullyMessage(){
        System.out.print(checkInSuccessfullyMessage);
    }

    private void showCheckInUnsuccesfullyMessage(){
        System.out.print(checkInUnsuccessfullyMessage);
    }

    private void showGeneralExceptionMessage(Exception exception){
        System.out.print(generalExceptionMessage + exception.toString());
    }
}
