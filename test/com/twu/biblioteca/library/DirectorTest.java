package com.twu.biblioteca.library;

import com.twu.biblioteca.library.movie.Director;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DirectorTest {

    @Test
    public void shouldCreateADirectorUsingItsName(){
        // Arrange
        String directorName = "Director";

        // Act
        Director director = new Director(directorName);

        // Assert
        assertThat(director.getName(), is(directorName));
    }
}
