package com.twu.biblioteca;

import java.util.ArrayList;

class Library {
    private String name;
    private ArrayList<Book> books;

    Library(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }

    boolean addBook(Book newBook){
        if(this.books == null)
            this.books = new ArrayList<>();
        this.books.add(newBook);
        return true;
    }

    Integer getBooksQuantity(){
        return this.books != null ? this.books.size() : 0;
    }

    String getBookList(){
        StringBuilder bookList = new StringBuilder();
        for(Book book : this.books){
            String bookInfo = book.getName() + "\n";
            bookList.append(bookInfo);
        }
        return bookList.toString();
    }
}

