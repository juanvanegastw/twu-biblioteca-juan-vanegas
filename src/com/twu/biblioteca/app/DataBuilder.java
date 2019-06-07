package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.rent.RentItemService;

class DataBuilder {
    protected RentItemService generateLibrary(){
        RentItemService rentItemService = new RentItemService("Biblioteca");
        rentItemService.addItem(new Book("First Book", 1992, "First Writer"));
        rentItemService.addItem(new Book("Second Book", 2019, "Second Writer"));
        rentItemService.addItem(new Book("Third Book", 2010, "Third Writer"));
        return rentItemService;
    }
}
