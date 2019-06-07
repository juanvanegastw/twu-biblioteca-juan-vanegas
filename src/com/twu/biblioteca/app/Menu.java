package com.twu.biblioteca.app;

import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.rent.RentItemException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Menu {

    private BufferedReader bufferedReader;
    private RentItemService rentBooksItemService;
    private RentItemService rentMoviesItemService;

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


    protected Menu (RentItemService rentBooksItemService, RentItemService rentMoviesItemService, BufferedReader reader){
        this.bufferedReader = (reader==null) ? new BufferedReader(new InputStreamReader(System.in)): reader;
        this.rentBooksItemService = rentBooksItemService;
        this.rentMoviesItemService = rentMoviesItemService;
    }

    protected void showTheListOfBooks(boolean justAvailable){
        showTheListOfRentItemService(this.rentBooksItemService, justAvailable);
    }

    protected void showTheListOfMovies(boolean justAvailable){
        showTheListOfRentItemService(this.rentMoviesItemService, justAvailable);
    }

    private void showTheListOfRentItemService(RentItemService rentItemService, boolean justAvailable){
        String bookList = rentItemService.getBookList(justAvailable);
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
                showTheListOfMovies(true);
                break;
            case "q":
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
            this.rentBooksItemService.checkOutBook(index);
            showCheckoutSuccesfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException| RentItemException exception) {
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
            this.rentBooksItemService.checkInBook(index);
            showCheckInSuccessfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException | RentItemException exception){
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
