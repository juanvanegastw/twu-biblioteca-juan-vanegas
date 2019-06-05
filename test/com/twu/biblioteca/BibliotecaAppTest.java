package com.twu.biblioteca;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayInputStream;
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
    public void shouldPrintANotNullMessageListingBooks(){
        //Setting
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        BibliotecaApp myApp = new BibliotecaApp();
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);

        //Executing
        myApp.listBooks();

        //Verifying
        Assert.assertTrue(byteArrayOutputStream.toString().length() > 0);

        // Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldShowTheGeneralMenu(){
        // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // Act
        myApp.showGeneralMenu();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), is(BibliotecaApp.generalMenu));

        // Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldShowTheBookListWhenSelecting1(){
        // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);
        String totalMessage = BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        myApp.parseOption("1");

        // Verifying
        assertThat( byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

         // Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldShowLeavingMessageWhenSelecting2(){
  // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // Act
        myApp.parseOption("2");

        // Verifying
        assertThat( byteArrayOutputStream.toString(), is(BibliotecaApp.leavingMessage));

        // Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldShowBookListWhenInserting1onConsole(){
        // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1\n".getBytes());
        myApp.userInteraction(byteArrayInputStream);

        // Verifying
        assertThat( byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

        // Cleaning
        System.setIn(System.in);
    }
    @Test
    public void shouldShowLeavingMessageWhenInserting2onConsole(){
        // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("2\r\n".getBytes());
        myApp.userInteraction(byteArrayInputStream);

        // Verifying
        assertThat( byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

        // Cleaning
        System.setIn(System.in);
    }

    @Test
    public void shouldShowNotRecognizedOptionWhenSelectingOtherOption(){
  // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // Act
        myApp.parseOption("5");

        // Verifying
        assertThat( byteArrayOutputStream.toString(), is(BibliotecaApp.invalidOptionMessage));

        // Cleaning
        System.setOut(System.out);
    }

    @Test
    public void shouldShowErrorMessageWhenInsertingANotRecognizedOptionOnConsole(){
        // Arrange
        BibliotecaApp myApp = new BibliotecaApp();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.invalidOptionMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Unrecognized\r\n".getBytes());
        myApp.userInteraction(byteArrayInputStream);

        // Verifying
        assertThat( byteArrayOutputStream.toString(), is(totalMessage));

        // Cleaning
        System.setIn(System.in);
    }
}
