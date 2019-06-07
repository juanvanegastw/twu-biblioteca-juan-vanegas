package com.twu.biblioteca.library.rent;

public class RentItemException extends Exception {
}

class ItemAlreadyCheckedOutException extends RentItemException {

}

class ItemAlreadyCheckedInException extends RentItemException {

}