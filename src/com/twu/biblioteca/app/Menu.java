package com.twu.biblioteca.app;

import com.twu.biblioteca.library.rent.RentItemService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Menu {

    private BufferedReader bufferedReader;
    private MenuService bookMenuService;
    private MenuService movieMenuService;

    protected static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";

    protected static String generalMenu = "\nSelect an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to show movie list \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert \"4\" to return a book\n" +
            "Insert \"q\" to quit \n" +
            "Insert and press Enter:\n";
    protected static String leavingMessage = "Thanks for using Biblioteca";
    protected static String invalidOptionMessage = "Please select a valid option!\n";
    protected static String generalExceptionMessage = "A error has happened and has thrown the next Exception:\n";


    protected Menu (RentItemService rentBookService, RentItemService rentMovieService, BufferedReader reader){
        this.bufferedReader = (reader==null) ? new BufferedReader(new InputStreamReader(System.in)): reader;
        this.bookMenuService = new MenuService(this, "book", rentBookService);
        this.movieMenuService = new MenuService(this, "movie", rentMovieService);
    }

    protected void showGeneralMenu(){
        System.out.print(generalMenu);
    }

    private void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    public void showTheListOfItems(MenuService menuService, boolean justAvailable){
        menuService.showTheListOfItems(justAvailable);
    }

    protected boolean executeUserSelectedOption(String option){
        boolean programMustContinue = true;
        switch (option){
            case "1":
                this.bookMenuService.showTheListOfItems(true);
                break;
            case "2":
                this.movieMenuService.showTheListOfItems(true);
                break;
            case "q":
                showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                this.bookMenuService.startUserInteractionToCheckOut();
                break;
            case "4":
                this.bookMenuService.startUserInteractionToCheckIn();
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    private void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    protected String readOptionSelected(){
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

    protected void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }


    protected void showGeneralExceptionMessage(Exception exception){
        System.out.print(generalExceptionMessage + exception.toString());
    }
}
