package com.twu.biblioteca.library.rent;

import com.twu.biblioteca.library.user.LibraryUser;

/**
 * RentItem me suena más a un nombre de método que al de una clase
 */
public class RentItem {
    public static String infoSeparator = ",";
    /**
     * Por qué RentItemservice accede directamente a este field
     */
    protected boolean isCheckOut = false;
    private Item item;
    private LibraryUser currentReservingUser;

    public RentItem(Item item){
        this.item = item;
    }
    
    public void setIsCheckOut(boolean state, LibraryUser user) throws RentItemException {
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


