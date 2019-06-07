package com.twu.biblioteca.library.movie;

import com.twu.biblioteca.library.rent.RentItem;

public class Movie extends RentItem {
    private String name;
    private Integer releaseYear;
    private Director director;
    private Integer rating;

    public Movie(String name, Integer releaseYear, String director, Integer rating){
        this.name = name;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.director = new Director(director);
    }

    public String[] getDataInfo(){
        return new String [] {this.name, String.valueOf(this.releaseYear) ,this.director.getName(), String.valueOf(this.rating)};
    }
}
