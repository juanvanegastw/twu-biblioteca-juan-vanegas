package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemService;

class DataBuilder {
    static protected RentItemService generateBooksRentService(){
        RentItemService rentItemService = new RentItemService("Biblioteca");
        rentItemService.addItem(new RentItem(new Book("First Book", 1992, "First Writer")));
        rentItemService.addItem(new RentItem(new Book("Second Book", 2019, "Second Writer")));
        rentItemService.addItem(new RentItem(new Book("Third Book", 2010, "Third Writer")));
        return rentItemService;
    }

    static protected RentItemService generateMoviesRentService(){
        RentItemService rentItemService = new RentItemService("Movies");
        Movie movie = new Movie("I Robot", 2016, "Director", 5);
        rentItemService.addItem(new RentItem(new Movie("First Movie", 2016, "Director", 5)));
        rentItemService.addItem(new RentItem(new Movie("Second Movie", 2016, "Director", 5)));
        rentItemService.addItem(new RentItem(new Movie("Third Movie", 2016, "Director", 5)));
        return rentItemService;
    }

}
