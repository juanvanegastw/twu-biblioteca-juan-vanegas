package com.twu.biblioteca.library;

import java.util.ArrayList;

public class Library {
    private String name;
    private ArrayList<Book> books;

    public Library(String name){
        this.name = name;
        this.books = new ArrayList<>();
    }

    public boolean addBook(Book newBook){
        this.books.add(newBook);
        return true;
    }

    public String getBookList(boolean justAvailableBooks){
        StringBuilder bookList = new StringBuilder();
        if (this.books != null){
            for(Book book : this.books){
                if (!book.getIsCheckOut() == justAvailableBooks){
                    String bookInfo = this.books.indexOf(book) + Book.infoSeparator + book.getInfo() + "\n";
                    bookList.append(bookInfo);
                }
            }
        }
        return bookList.toString();
    }

    public void checkOutBook(int index) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        this.books.get(index).setIsCheckOut(true);
    }

    public void checkInBook(int index) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        this.books.get(index).setIsCheckOut(false);
    }

}

