package com.twu.biblioteca.library;

class Writer {

    private String name;

    protected Writer(String name){
        this.name = name;
    }

    protected String getName(){
        return this.name;
    }
}
