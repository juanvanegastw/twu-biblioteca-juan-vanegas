package com.twu.biblioteca;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class BibliotecaAppTest {

    @Test
    public void testWelcomeMessageNotNull(){
        //Setting
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(arrayStream));

        //Executing
        myApp.showWelcomeMessage();

        //Verifying
        Assert.assertTrue(arrayStream.toString().length() > 0);
        //Cleaning
        System.setOut(System.out);
    }

    @Test
    public void testWelcomeMessageCorrectMessage(){
        //Setting
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(arrayStream));

        //Executing
        myApp.showWelcomeMessage();

        //Verifying
        assertThat(arrayStream.toString(), is(BibliotecaApp.welcomeMessage));

        //Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldListAllBooksInTheALibrary(){
        //Setting
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        BibliotecaApp myApp = new BibliotecaApp();
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);

        //Executing
        myApp.listBooks();

        //Verifying
        assertThat(byteArrayOutputStream.toString(), is("First Book\n"));

        // Cleaning
        System.setOut(System.out);

    }
}
