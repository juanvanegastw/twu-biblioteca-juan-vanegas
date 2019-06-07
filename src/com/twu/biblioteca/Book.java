package com.twu.biblioteca;

class Book extends LibraryRentItem{
    private Integer publicationYear;
    private Writer author;
    Book(String name) {
        this.name = name;
    }

    Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);

    }

    String[] getDataInfo(){
        if (this.publicationYear != null && this.author != null)
        {
            return new String [] {this.name, this.author.getName(), String.valueOf(this.publicationYear)};
        }
        return super.getDataInfo();
    }
}


