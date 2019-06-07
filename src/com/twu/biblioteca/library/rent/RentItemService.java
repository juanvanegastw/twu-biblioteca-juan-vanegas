package com.twu.biblioteca.library.rent;


import com.twu.biblioteca.library.book.Book;

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

    public String getBookList(boolean justAvailableBooks){
        StringBuilder itemList = new StringBuilder();
        if (this.items != null){
            for(RentItem item : this.items){
                if (!item.getIsCheckOut() == justAvailableBooks){
                    String bookInfo = this.items.indexOf(item) + Book.infoSeparator + item.getInfo() + "\n";
                    itemList.append(bookInfo);
                }
            }
        }
        return itemList.toString();
    }

    public void checkOutBook(int index) throws RentItemException {
        this.items.get(index).setIsCheckOut(true);
    }

    public void checkInBook(int index) throws RentItemException {
        this.items.get(index).setIsCheckOut(false);
    }

}

