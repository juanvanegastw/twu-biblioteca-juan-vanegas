package com.twu.biblioteca.app;

import com.twu.biblioteca.library.book.Book;
import com.twu.biblioteca.library.item.RentalItem;
import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.movie.Movie;
import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;

/**
 * Sería mejor separar responsabilidades por el tipo de objeto que se esta generando y crear clases específicas para los mismos
 */
class DataBuilder {
    static public RentalItemService generateBooksRentService(){
        RentalItemService rentalItemService = new RentalItemService("Biblioteca");
        rentalItemService.addItem(new RentalItem(new Book("First Book", 1992, "First Writer")));
        rentalItemService.addItem(new RentalItem(new Book("Second Book", 2019, "Second Writer")));
        rentalItemService.addItem(new RentalItem(new Book("Third Book", 2010, "Third Writer")));
        return rentalItemService;
    }

    static public RentalItemService generateMoviesRentService(){
        RentalItemService rentalItemService = new RentalItemService("Movies");
        Movie movie = new Movie("I Robot", 2016, "Director", 5);
        rentalItemService.addItem(new RentalItem(new Movie("First Movie", 2016, "Director", 5)));
        rentalItemService.addItem(new RentalItem(new Movie("Second Movie", 2016, "Director", 5)));
        rentalItemService.addItem(new RentalItem(new Movie("Third Movie", 2016, "Director", 5)));
        return rentalItemService;
    }

    static public LogIn generateLogIn() throws UserException {

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
