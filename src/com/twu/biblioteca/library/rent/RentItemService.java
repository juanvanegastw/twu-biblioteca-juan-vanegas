package com.twu.biblioteca.library.rent;


import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.user.LibraryUser;

import java.util.ArrayList;

public class RentItemService {
    private String name;
    private ArrayList<RentItem> items;

    public RentItemService(String name){
        this.name = name;
        this.items = new ArrayList<>();
    }

    public boolean addItem(RentItem newItem){
        this.items.add(newItem);
        return true;
    }

    public String getItemList(boolean justAvailableItems){
        StringBuilder itemList = new StringBuilder();
        if (this.items != null){
            for(RentItem item : this.items){
                if (!item.getIsCheckOut() == justAvailableItems){
                    String bookInfo = this.items.indexOf(item) + RentItem.infoSeparator + item.getInfo() + "\n";
                    itemList.append(bookInfo);
                }
            }
        }
        return itemList.toString();
    }

    public void checkOutItem(int index, LibraryUser user) throws RentItemException {
        this.items.get(index).setIsCheckOut(true, user);
    }

    public void checkInItem(int index) throws RentItemException {
        this.items.get(index).setIsCheckOut(false, null);
    }

}

