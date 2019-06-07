package com.twu.biblioteca.library;

public class Book {
    private String name;
    private Integer publicationYear;
    private Writer author;
    private boolean isCheckOut = false;
    static String infoSeparator = ",";

    public Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);
    }

    String getInfo(){
        return String.format("%s" + infoSeparator + "%s" + infoSeparator + "%d",
                    this.name, this.author.getName(), this.publicationYear );
    }

    void setIsCheckOut(boolean state) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        if (state && this.isCheckOut )
        {
            throw new BookAlreadyCheckedOutException();
        }
        else if (!state && !this.isCheckOut)
        {
            throw new BookAlreadyCheckedInException();
        }
        this.isCheckOut = state;
    }

    public boolean getIsCheckOut(){
        return this.isCheckOut;
    }
}
