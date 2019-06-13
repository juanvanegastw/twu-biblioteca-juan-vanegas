package com.twu.biblioteca.app;

import com.twu.biblioteca.library.user.LibraryUser;

public class MenuMessages {
    public static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";
    public static String userInfoOption = "Insert \"p\" to see the user profile \n";
    public static String userInfoMessage = "Your data is:\n";
    public static String selectOptionMessage = "Insert and press Enter:\n";
    public static String leavingMessage = "Thanks for using Biblioteca";
    public static String invalidOptionMessage = "Please select a valid option!\n";
    public static String generalExceptionMessage = "A error has happened and has thrown the next Exception:\n";
    public static String generalMenu = "\nSelect an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to show movie list \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert \"4\" to return a book\n" +
            "Insert \"5\" to check out a movie \n" +
            "Insert \"6\" to return a movie\n" +
            "Insert \"l\" to log out \n" +
            "Insert \"q\" to quit \n";


    public static void showGeneralExceptionMessage(Exception exception){
        System.out.print(generalExceptionMessage + exception.toString());
    }

    public static void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    public static void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    public static void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }


    private static String getMenu(LibraryUser user){
        if(user != null){
            return generalMenu + userInfoOption + selectOptionMessage;
        }
        else
        {
            return generalMenu + selectOptionMessage;
        }
    }

    public static void showGeneralMenu(LibraryUser user){
        System.out.print(getMenu(user));
    }
}
