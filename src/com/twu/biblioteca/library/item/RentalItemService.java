package com.twu.biblioteca.library.item;
import com.twu.biblioteca.library.user.LibraryUser;
import java.util.ArrayList;

public class RentalItemService {
    private String name;
    private ArrayList<RentalItem> items;

    public RentalItemService(String name){
        this.name = name;
        this.items = new ArrayList<>();
    }

    public boolean addItem(RentalItem newItem){
        this.items.add(newItem);
        return true;
    }

    public String getItemList(boolean justAvailableItems){
        StringBuilder itemList = new StringBuilder();
        if (this.items != null){
            for(RentalItem item : this.items){
                if (!item.getIsCheckOut() == justAvailableItems){
                    String bookInfo = this.items.indexOf(item) + RentalItem.infoSeparator + item.getInfo() + "\n";
                    itemList.append(bookInfo);
                }
            }
        }
        return itemList.toString();
    }

    public void checkOutItem(int index, LibraryUser user) throws RentalItemException {
        this.items.get(index).setIsCheckOut(true, user);
    }

    public void checkInItem(int index) throws RentalItemException {
        this.items.get(index).setIsCheckOut(false, null);
    }
}

