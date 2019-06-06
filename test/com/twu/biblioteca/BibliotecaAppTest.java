package com.twu.biblioteca;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.Assert;
import static org.hamcrest.CoreMatchers.*;

import java.io.*;
import java.io.BufferedReader;

import static org.mockito.Mockito.*;


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
    public void shouldShowBookListWhenInserting1onConsole() throws IOException {
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage +
                myApp.biblioteca.getBookList() + BibliotecaApp.generalMenu + BibliotecaApp.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act
        myApp.userInteraction();

        // Verifying
        assertThat( byteArrayOutputStream.toString().split("\n").length, is(messageLinesNumber));

        // Cleaning
        System.setIn(System.in);
    }
    @Test
    public void shouldShowLeavingMessageWhenInserting2onConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book");
        myApp.biblioteca.addBook(firstBook);
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.leavingMessage;
        Integer messageLinesNumber = totalMessage.split("\n").length;

        // Act

        when(bufferedReader.readLine()).thenReturn("2");
        myApp.userInteraction();

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
    public void shouldShowErrorMessageWhenInsertingANotRecognizedOptionOnConsole() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Another").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        String totalMessage = BibliotecaApp.generalMenu + BibliotecaApp.invalidOptionMessage
                + BibliotecaApp.generalMenu + BibliotecaApp.leavingMessage;

        // Act

        myApp.userInteraction();

        // Verifying
        assertThat( byteArrayOutputStream.toString(), is(totalMessage));

        // Cleaning
        System.setIn(System.in);
    }

    @Test
    public void shouldShowBookListAndThenCloseWhenSelecting1andthen2() throws IOException{
        // Arrange
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        String firstlMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        String secondMessage = BibliotecaApp.generalMenu + BibliotecaApp.leavingMessage;
        String totalMessage = firstlMessage + secondMessage;

        // Act

        myApp.userInteraction();

        // Verifying
        assertThat( byteArrayOutputStream.toString(), is(totalMessage));

        // Cleaning
        System.setIn(System.in);
    }
}
