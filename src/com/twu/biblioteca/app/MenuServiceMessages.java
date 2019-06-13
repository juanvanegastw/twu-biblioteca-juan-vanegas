package com.twu.biblioteca.app;

public class MenuServiceMessages {

    public static String selectItemMessage = "\nPlease select the index of the %s you want to Check Out\n";
    public static String selectItemToReturnMessage = "\nPlease select the index of the %s you want to Return\n";
    public static String checkoutSuccessfullyMessage = "Thank you! Enjoy the %s\n";
    public static String checkoutUnsuccessfullyMessage = "Sorry, that %s is not available\n";
    public static String checkInSuccessfullyMessage = "Thank you for returning the %s\n";
    public static String checkInUnsuccessfullyMessage = "That is not a valid %s to return\n";
    public static String availableItemsMessage = "Here you can see a list of the %s\n\n";
    public static String reservedItemsMessage = "Here you can see a list of the reserved %s\n\n";


    public static void showSelectItemMessage(String itemName) {
        printMessage(selectItemMessage, itemName);
    }

    public static void showSelectItemToReturnMessage(String itemName) {
        printMessage(selectItemToReturnMessage, itemName);
    }

    public static void showCheckoutSuccessfullyMessage(String itemName){
        printMessage(checkoutSuccessfullyMessage, itemName);
    }

    public static void showCheckoutUnsuccessfullyMessage(String itemName){
        printMessage(checkoutUnsuccessfullyMessage, itemName);
    }

    public static void showCheckInSuccessfullyMessage(String itemName){
        printMessage(checkInSuccessfullyMessage, itemName);
    }

    public static void showCheckInUnsuccessfullyMessage(String itemName){
        printMessage(checkInUnsuccessfullyMessage, itemName);
    }

    private static void printMessage(String message, String itemName){
        String outMessage = String.format(message, itemName);
        System.out.print(outMessage);
    }

    public static void showAvailableItemsMessage(String itemName){
        printMessage(availableItemsMessage, itemName);
    }

    public static void showReservedItemsMessage(String itemName){
        printMessage(reservedItemsMessage, itemName);
    }
}
