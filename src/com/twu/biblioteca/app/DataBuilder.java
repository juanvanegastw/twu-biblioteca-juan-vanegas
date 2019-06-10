package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.rent.RentItem;
import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;


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

    static protected LogIn generateLogIn() throws UserException {

        LogIn logIn= new LogIn(null);
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "1", "Juan", "juan@email.com", "094");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2", "Maria", "maria@email.com", "095");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3", "Pedro", "pedro@email.com", "096");
        logIn.addValidUser(thirdLibraryUser);
        return logIn;
    }

}
