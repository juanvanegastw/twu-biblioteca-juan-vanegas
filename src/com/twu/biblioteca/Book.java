package com.twu.biblioteca;

class Book {
    private String name;
    private Integer publicationYear;
    private Writer author;
    String infoSeparator = ",";
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
            return String.format("%s" + this.infoSeparator + "%s" + this.infoSeparator + "%d",
                                this.name, this.author.getName(), this.publicationYear );
        return this.name;
    }
}