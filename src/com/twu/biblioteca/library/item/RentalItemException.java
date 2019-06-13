package com.twu.biblioteca.library.item;

public class RentalItemException extends Exception {
}

class ItemAlreadyCheckedOutException extends RentalItemException {
}

class ItemAlreadyCheckedInException extends RentalItemException {
}