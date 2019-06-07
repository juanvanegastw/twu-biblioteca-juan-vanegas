package com.twu.biblioteca;

/**
 * Definir los modificadores de acceso de todos los métodos.
 */
public class Writer {

    private String name;

    Writer(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }
}
