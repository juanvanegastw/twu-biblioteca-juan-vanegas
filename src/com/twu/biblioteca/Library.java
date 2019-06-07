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
        return getBookList(true);
    }

    String getBookList(boolean justAvailableBooks){
        StringBuilder bookList = new StringBuilder();
        if (this.books != null){
            for(Book book : this.books){
                boolean isAvailable = !book.getIsCheckOut();
                boolean mustReturn = isAvailable == justAvailableBooks;
                if (mustReturn){
                    String bookInfo = this.books.indexOf(book) + Book.infoSeparator + book.getInfo() + "\n";
                    bookList.append(bookInfo);
                }
            }
        }
        return bookList.toString();

    }

    void checkOutBook(int index) throws ItemAlreadyCheckedOutException, ItemAlreadyCheckedInException {
        this.books.get(index).setIsCheckOut(true);
    }

    void checkInBook(int index) throws ItemAlreadyCheckedOutException, ItemAlreadyCheckedInException {
        this.books.get(index).setIsCheckOut(false);
    }

}

