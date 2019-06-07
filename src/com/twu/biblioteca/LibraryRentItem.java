package com.twu.biblioteca;

public class LibraryRentItem {
    protected String name;
    static String infoSeparator = ",";
    protected boolean isCheckOut = false;

    String[] getDataInfo(){
        return new String [] {this.name};
    }

    void setIsCheckOut(boolean state) throws ItemAlreadyCheckedOutException, ItemAlreadyCheckedInException {
        if (state && this.isCheckOut )
        {
            throw new ItemAlreadyCheckedOutException();
        }
        else if (!state && !this.isCheckOut)
        {
            throw new ItemAlreadyCheckedInException();
        }
        this.isCheckOut = state;
    }

    String getName(){return this.name;}

    boolean getIsCheckOut(){
        return this.isCheckOut;
    }

    String getInfo(){
        return String.join(infoSeparator, getDataInfo());
    }

}

class ItemAlreadyCheckedOutException extends Exception{

}

class ItemAlreadyCheckedInException extends Exception{

}
