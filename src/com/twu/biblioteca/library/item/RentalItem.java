package com.twu.biblioteca.library.item;

import com.twu.biblioteca.library.user.LibraryUser;

public class RentalItem {
    public static String infoSeparator = ",";
    private boolean isCheckOut = false;
    private Item item;
    private LibraryUser currentReservingUser;

    public RentalItem(Item item){
        this.item = item;
    }
    
    public void setIsCheckOut(boolean state, LibraryUser user) throws RentalItemException {
        if (state && this.isCheckOut )
        {
            throw new ItemAlreadyCheckedOutException();
        }
        else if (!state && !this.isCheckOut)
        {
            throw new ItemAlreadyCheckedInException();
        }
        this.isCheckOut = state;
        if (this.isCheckOut){
            this.currentReservingUser = user;
        }
    }

    public LibraryUser getCurrentReservingUser(){
        return this.currentReservingUser;
    }

    public boolean getIsCheckOut(){
        return this.isCheckOut;
    }

    public String getInfo(){
        String info = String.join(infoSeparator, this.item.getDataInfo());
        if (this.isCheckOut && this.currentReservingUser != null){
            info = info + infoSeparator + "Reserved by: " + this.currentReservingUser.getLibraryNumber();
        }
        return info;
    }

}


