package com.twu.biblioteca;

import java.util.ArrayList;

/**
 * Usar paquetes para organizar mejor las clases.
 * Definir los modificadores de acceso de todos los métodos.
 */
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
        // ¿Y si inicializas books en el constructor?
        if(this.books == null)
            this.books = new ArrayList<>();
        this.books.add(newBook);
        return true;
    }

    /**
     * Este método solo se usa en las pruebas removerlo.
     */
    Integer getBooksQuantity(){
        return this.books != null ? this.books.size() : 0;
    }

    /**
     * Este método solo se usa en las pruebas removerlo.
     */
    String getBookList(){
        return getBookList(true);
    }

    String getBookList(boolean justAvailableBooks){
        StringBuilder bookList = new StringBuilder();
        if (this.books != null){
            for(Book book : this.books){
                boolean isAvailable = !book.getIsCheckOut();
                // Es innecesario declarar una variable para esta comparación, usualmente los haces cuando son más de dos.
                boolean mustReturn = isAvailable == justAvailableBooks;
                if (mustReturn){
                    String bookInfo = this.books.indexOf(book) + Book.infoSeparator + book.getInfo() + "\n";
                    bookList.append(bookInfo);
                }
            }
        }
        return bookList.toString();
    }

    void checkOutBook(int index) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        this.books.get(index).setIsCheckOut(true);
    }

    void checkInBook(int index) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        this.books.get(index).setIsCheckOut(false);
    }

}

