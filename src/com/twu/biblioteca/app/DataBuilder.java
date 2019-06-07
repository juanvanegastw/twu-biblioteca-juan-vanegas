package com.twu.biblioteca.app;

import com.twu.biblioteca.library.Book;
import com.twu.biblioteca.library.Library;

class DataBuilder {
    protected Library generateLibrary(){
        Library library = new Library("Biblioteca");
        library.addBook(new Book("First Book", 1992, "First Writer"));
        library.addBook(new Book("Second Book", 2019, "Second Writer"));
        library.addBook(new Book("Third Book", 2010, "Third Writer"));
        return library;
    }
}
