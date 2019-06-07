package com.twu.biblioteca;

/**
 * Usar paquetes para organizar mejor las clases.
 * Extraer las clases internas a nuevas clases.
 * Definir los modificadores de acceso de todos los métodos.
 */
class Book {
    private String name;
    private Integer publicationYear;
    private Writer author;
    private boolean isCheckOut = false;
    static String infoSeparator = ",";

    /**
     * Este constructor sólo lo usan las pruebas, removerlo
     */
    Book(String name) {
        this.name = name;
    }

    Book(String name, Integer publicationYear, String authorName){
        this.publicationYear = publicationYear;
        this.name = name;
        this.author = new Writer(authorName);
    }

    /**
     * Este método sólo se usa en las pruebas, removerlo
     */
    String getName(){
        return this.name;
    }

    /**
     * ¿Qué pasa cuando publicationYear y/o autor son null?, ¿debería lanzar una excepción?
     */
    String getInfo(){
        if (this.publicationYear != null && this.author != null)
            return String.format("%s" + infoSeparator + "%s" + infoSeparator + "%d",
                    this.name, this.author.getName(), this.publicationYear );
        return this.name;
    }

    void setIsCheckOut(boolean state) throws BookAlreadyCheckedOutException, BookAlreadyCheckedInException{
        if (state && this.isCheckOut )
        {
            throw new BookAlreadyCheckedOutException();
        }
        else if (!state && !this.isCheckOut)
        {
            throw new BookAlreadyCheckedInException();
        }
        this.isCheckOut = state;
    }

    boolean getIsCheckOut(){
        return this.isCheckOut;
    }
}

class BookAlreadyCheckedOutException extends Exception{

}

class BookAlreadyCheckedInException extends Exception{

}

