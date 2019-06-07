package com.twu.biblioteca.library;

public class LibraryException extends Exception {
}

class ItemAlreadyCheckedOutException extends LibraryException{

}

class ItemAlreadyCheckedInException extends LibraryException{

}