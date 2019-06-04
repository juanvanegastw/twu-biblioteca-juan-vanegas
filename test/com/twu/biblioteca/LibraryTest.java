package com.twu.biblioteca;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LibraryTest {
    @Test
    public void shouldAllowToCreateALibraryWithItsName(){
        // Setting
        Library myLibrary = new Library("Awesome Library");

        // Executing
        String libraryName = myLibrary.getName();

        // Verifying
        assertThat(libraryName, is("Awesome Library"));

    }

}


