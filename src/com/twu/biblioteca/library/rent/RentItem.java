package com.twu.biblioteca.library.rent;

public class RentItem {
    public static String infoSeparator = ",";
    protected boolean isCheckOut = false;
    private Item item;

    public RentItem(Item item){
        this.item = item;
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
        return String.join(infoSeparator, this.item.getDataInfo());
    }

}


