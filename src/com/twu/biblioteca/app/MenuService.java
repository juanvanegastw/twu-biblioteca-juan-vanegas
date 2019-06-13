package com.twu.biblioteca.app;

import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.user.LibraryUser;

public class MenuService {
    private String itemName;
    private RentItemService rentItemService;
    private Menu menu;

    /**
     * ¿Por qué estos fields y métodos deben ser protected?
     */
    protected static String selectItemMessage = "\nPlease select the index of the %s you want to Check Out\n";
    protected static String selectItemToReturnMessage = "\nPlease select the index of the %s you want to Return\n";
    protected static String checkoutSuccessfullyMessage = "Thank you! Enjoy the %s\n";
    protected static String checkoutUnsuccessfullyMessage = "Sorry, that %s is not available\n";
    protected static String checkInSuccessfullyMessage = "Thank you for returning the %s\n";
    protected static String checkInUnsuccessfullyMessage = "That is not a valid %s to return\n";
    protected static String availableItemsMessage = "Here you can see a list of the %s\n\n";
    protected static String reservedItemsMessage = "Here you can see a list of the reserved %s\n\n";

    public MenuService(Menu menu, String itemName, RentItemService rentItemService){
        this.menu = menu;
        this.itemName = itemName;
        this.rentItemService = rentItemService;
    }

    /**
     * Estos métodos sólo se usan en esta clase ¿Cuál es el modificador de acceso que deberían de usar?
     */
    public void showSelectItemMessage() {
        printMessage(selectItemMessage);
    }

    public void showSelectItemToReturnMessage() {
        printMessage(selectItemToReturnMessage);
    }

    public void showCheckoutSuccessfullyMessage(){
        printMessage(checkoutSuccessfullyMessage);
    }

    public void showCheckoutUnsuccessfullyMessage(){
        printMessage(checkoutUnsuccessfullyMessage);
    }

    public void showCheckInSuccessfullyMessage(){
        printMessage(checkInSuccessfullyMessage);
    }

    public void showCheckInUnsuccessfullyMessage(){
        printMessage(checkInUnsuccessfullyMessage);
    }

    public void printMessage(String message){
        String outMessage = String.format(message, this.itemName);
        System.out.print(outMessage);
    }

    /**
     * Este método sólo se usa en esta clase ¿Por qué es protected?
     */
    protected void checkOutAItem(LibraryUser user) {
        try {
            String itemIndex = this.menu.readOptionSelected();
            int index = Integer.valueOf(itemIndex);
            this.rentItemService.checkOutItem(index, user);
            showCheckoutSuccessfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException| RentItemException exception) {
            showCheckoutUnsuccessfullyMessage();
        }
        catch (Exception exception){
            this.menu.showGeneralExceptionMessage(exception);
        }
    }

    /**
     * Este método se usa en la clase Menu ¿Por qué es protected?, ¿Cuál es el mofidicador de acceso correcto que debería utilizar?
     */
    protected void startUserInteractionToCheckOut(LibraryUser user){
        this.showTheListOfItems(true);
        this.showSelectItemMessage();
        this.checkOutAItem(user);
    }

    /**
     * Este método se usa en la clase Menu ¿Por qué es protected?, ¿Cuál es el mofidicador de acceso correcto que debería utilizar?
     */
    protected void startUserInteractionToCheckIn(){
        this.showTheListOfItems(false);
        this.showSelectItemToReturnMessage();
        this.checkInAItem();
    }

    /**
     * Este método sólo se usa en esta clase ¿Por qué es protected?
     */
    protected void checkInAItem(){
        try {
            String itemIndex = this.menu.readOptionSelected();
            int index = Integer.valueOf(itemIndex);
            this.rentItemService.checkInItem(index);
            showCheckInSuccessfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException | RentItemException exception){
            showCheckInUnsuccessfullyMessage();
        }
        catch (Exception exception){
            Menu.showGeneralExceptionMessage(exception);
        }
    }

    private void showTheListOfRentItemService(RentItemService rentItemService, boolean justAvailable){
        String itemList = rentItemService.getItemList(justAvailable);
        if (justAvailable) {
            printMessage(availableItemsMessage);
        }
        else{
            printMessage(reservedItemsMessage);
        }
        System.out.print(itemList);
    }

    /**
     * ¿Cuál es el propósito de este método?, ¿Por qué no usar directamente el field rentItemService?
     */
    protected void showTheListOfItems(boolean justAvailable){
        showTheListOfRentItemService(this.rentItemService, justAvailable);
    }
}
