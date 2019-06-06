package com.twu.biblioteca;

class Book {
    private String name;
    private Integer publicationYear;
    private Writer author;
    private boolean isCheckOut = false;
    static String infoSeparator = ",";
    Book(String name) {
        this.name = name;
    }

    Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);

    }

    String getName(){
        return this.name;
    }

    String getInfo(){
        if (this.publicationYear != null && this.author != null)
            return String.format("%s" + infoSeparator + "%s" + infoSeparator + "%d",
                                this.name, this.author.getName(), this.publicationYear );
        return this.name;
    }

    void setIsCheckOut(boolean state) throws BookAlreadyCheckedOutException{
        if (state && this.isCheckOut )
        {
            throw new BookAlreadyCheckedOutException();
        }
        this.isCheckOut = state;
    }

    boolean getIsCheckOut(){
        return this.isCheckOut;
    }
}

class BookAlreadyCheckedOutException extends Exception{

}

