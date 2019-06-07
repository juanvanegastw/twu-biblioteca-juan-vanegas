package com.twu.biblioteca.library;

public class LibraryException extends Exception {
}

class BookAlreadyCheckedOutException extends LibraryException{

}

class BookAlreadyCheckedInException extends LibraryException{

}