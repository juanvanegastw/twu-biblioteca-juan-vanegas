package com.twu.biblioteca.app;

import com.twu.biblioteca.library.item.RentalItemException;
import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.user.LibraryUser;

public class MenuService {
    private String itemName;
    private RentalItemService rentalItemService;
    private Menu menu;

    public MenuService(Menu menu, String itemName, RentalItemService rentalItemService){
        this.menu = menu;
        this.itemName = itemName;
        this.rentalItemService = rentalItemService;
    }

    private void checkOutAItem(LibraryUser user) {
        try {
            String itemIndex = this.menu.readOptionSelected();
            int index = Integer.valueOf(itemIndex);
            this.rentalItemService.checkOutItem(index, user);
            MenuServiceMessages.showCheckoutSuccessfullyMessage(itemName);
        }
        catch (NumberFormatException | IndexOutOfBoundsException| RentalItemException exception) {
            MenuServiceMessages.showCheckoutUnsuccessfullyMessage(itemName);
        }
        catch (Exception exception){
            MenuMessages.showGeneralExceptionMessage(exception);
        }
    }

    public void startUserInteractionToCheckOut(LibraryUser user){
        this.showTheListOfRentItemService(true);
        MenuServiceMessages.showSelectItemMessage(itemName);
        this.checkOutAItem(user);
    }


    public void startUserInteractionToCheckIn(){
        this.showTheListOfRentItemService(false);
        MenuServiceMessages.showSelectItemToReturnMessage(itemName);
        this.checkInAItem();
    }

    private void checkInAItem(){
        try {
            String itemIndex = this.menu.readOptionSelected();
            int index = Integer.valueOf(itemIndex);
            this.rentalItemService.checkInItem(index);
            MenuServiceMessages.showCheckInSuccessfullyMessage(itemName);
        }
        catch (NumberFormatException | IndexOutOfBoundsException | RentalItemException exception){
            MenuServiceMessages.showCheckInUnsuccessfullyMessage(itemName);
        }
        catch (Exception exception){
            MenuMessages.showGeneralExceptionMessage(exception);
        }
    }

    public void showTheListOfRentItemService(boolean justAvailable){
        String itemList = rentalItemService.getItemList(justAvailable);
        if (justAvailable) {
            MenuServiceMessages.showAvailableItemsMessage(itemName);
        }
        else{
            MenuServiceMessages.showReservedItemsMessage(itemName);
        }
        System.out.print(itemList);
    }

}
