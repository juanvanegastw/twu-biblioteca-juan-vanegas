package com.twu.biblioteca.app;

import com.twu.biblioteca.library.rent.RentItemException;
import com.twu.biblioteca.library.rent.RentItemService;

public class MenuService {
    private String itemName;
    private RentItemService rentItemService;
    private Menu menu;
    protected static String selectItemMessage = "\nPlease select the index of the %s you want to Check Out\n";
    protected static String selectItemToReturnMessage = "\nPlease select the index of the %s you want to Return\n";
    protected static String checkoutSuccessfullyMessage = "Thank you! Enjoy the %s\n";
    protected static String checkoutUnsuccessfullyMessage = "Sorry, that %s is not available\n";
    protected static String checkInSuccessfullyMessage = "Thank you for returning the %s\n";
    protected static String checkInUnsuccessfullyMessage = "That is not a valid %s to return\n";
    protected static String availableItemsMessage = "Here you can see a list of the %s\n\n";
    protected static String missingItemsMessage = "Here you can see a list of the missing %s\n\n";

    public MenuService(Menu menu, String itemName, RentItemService rentItemService){
        this.menu = menu;
        this.itemName = itemName;
        this.rentItemService = rentItemService;
    }

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

    protected void checkOutAItem() {
        try {
            String itemIndex = this.menu.readOptionSelected();
            int index = Integer.valueOf(itemIndex);
            this.rentItemService.checkOutItem(index);
            showCheckoutSuccessfullyMessage();
        }
        catch (NumberFormatException | IndexOutOfBoundsException| RentItemException exception) {
            showCheckoutUnsuccessfullyMessage();
        }
        catch (Exception exception){
            this.menu.showGeneralExceptionMessage(exception);
        }
    }

    protected void startUserInteractionToCheckOut(){
        this.showTheListOfItems(true);
        this.showSelectItemMessage();
        this.checkOutAItem();
    }

    protected void startUserInteractionToCheckIn(){
        this.showTheListOfItems(false);
        this.showSelectItemToReturnMessage();
        this.checkInAItem();
    }

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
            this.menu.showGeneralExceptionMessage(exception);
        }
    }

    private void showTheListOfRentItemService(RentItemService rentItemService, boolean justAvailable){
        String itemList = rentItemService.getItemList(justAvailable);
        if (justAvailable) {
            printMessage(availableItemsMessage);
        }
        else{
            printMessage(missingItemsMessage);
        }
        System.out.print(itemList);
    }

    protected void showTheListOfItems(boolean justAvailable){
        showTheListOfRentItemService(this.rentItemService, justAvailable);
    }
}
