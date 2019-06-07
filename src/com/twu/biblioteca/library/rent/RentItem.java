package com.twu.biblioteca.library.rent;

public class RentItem {
    protected String name;
    public static String infoSeparator = ",";
    protected boolean isCheckOut = false;

    public String[] getDataInfo(){
        return new String [] {this.name};
    }

    public void setIsCheckOut(boolean state) throws RentItemException {
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

    public boolean getIsCheckOut(){
        return this.isCheckOut;
    }

    public String getInfo(){
        return String.join(infoSeparator, getDataInfo());
    }

}

