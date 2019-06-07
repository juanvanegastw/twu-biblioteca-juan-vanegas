package com.twu.biblioteca.library;


public class Book extends LibraryRentItem{
    private Integer publicationYear;
    private Writer author;

    public Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);
    }

    String[] getDataInfo(){
        return new String [] {this.name, this.author.getName(), String.valueOf(this.publicationYear)};
    }

}

