package com.twu.biblioteca.library.book;
import com.twu.biblioteca.library.rent.Item;

public class Book implements Item {
    private String name;
    private Integer publicationYear;
    private Writer author;

    public Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);
    }

    public String[] getDataInfo(){
        return new String [] {this.name, this.author.getName(), String.valueOf(this.publicationYear)};
    }
}

