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
    public void shouldShowTheBookListWhenSelecting1() throws IOException{
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
    public void shouldShowLeavingMessageWhenSelecting2() throws IOException{
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
    public void shouldShowNotRecognizedOptionWhenSelectingOtherOption() throws IOException{
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

    /*
    * CHECKING OUT BOOKS
    * */

    @Test
    public void shouldShowCheckOutMenuWhenSelectinOption3() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("x").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        String secondMessage = BibliotecaApp.selectBookMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldCheckOutBookWhenSelecting0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("0");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);


        // Act
        myApp.userCheckingOutBook();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(true));

        // Cleaning
        System.setIn(System.in);

    }


    @Test
    public void shouldCheckOutBookWhenSelecting3AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(true));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting3AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        String secondMessage = BibliotecaApp.selectBookMessage + BibliotecaApp.checkoutSuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenAnIndexOutOfArray() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("1").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        String secondMessage = BibliotecaApp.selectBookMessage + BibliotecaApp.checkoutUnsuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowUnsuccessfulMessageWhenSelecting3AndThenACharacterNotParseToInt() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("x").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.availableBooksMessage + myApp.biblioteca.getBookList();
        String secondMessage = BibliotecaApp.selectBookMessage + BibliotecaApp.checkoutUnsuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    /*
    * CHECKING IN
    *
    * */


    @Test
    public void shouldShowCheckInMenuWhenSelectinOption4() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("x").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.missingBooksMessage + myApp.biblioteca.getBookList(false);
        String secondMessage = BibliotecaApp.selectBookToReturnMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldReturnBookWhenSelecting0() throws IOException, BookAlreadyCheckedOutException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("0");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);


        // Act
        myApp.userCheckingOutBook();
        myApp.userCheckInBook();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(false));

        // Cleaning
        System.setIn(System.in);

    }
    @Test
    public void shouldCheckOutBookWhenSelecting3then0then4AndThen0() throws IOException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("3").thenReturn("0").thenReturn("4").thenReturn("0").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(firstBook.getIsCheckOut(), is(false));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThen0() throws IOException, BookAlreadyCheckedInException, BookAlreadyCheckedOutException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("0").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        myApp.biblioteca.checkOutBook(0);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.missingBooksMessage + myApp.biblioteca.getBookList(false);
        String secondMessage = BibliotecaApp.selectBookToReturnMessage + BibliotecaApp.checkInSuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenANotMissingBook() throws IOException, BookAlreadyCheckedInException, BookAlreadyCheckedOutException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("0").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.missingBooksMessage + myApp.biblioteca.getBookList(false);
        String secondMessage = BibliotecaApp.selectBookToReturnMessage + BibliotecaApp.checkInUnsuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }

    @Test
    public void shouldShowSuccessfulMessageWhenSelecting4AndThenABookOutOfIndex() throws IOException, BookAlreadyCheckedInException, BookAlreadyCheckedOutException{
        // Arrange
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when((bufferedReader.readLine())).thenReturn("4").thenReturn("1").thenReturn("2");
        BibliotecaApp myApp = new BibliotecaApp(bufferedReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
        Book firstBook = new Book("First Book", 1992, "First Writer");
        myApp.biblioteca.addBook(firstBook);
        String firstMessage = BibliotecaApp.generalMenu + BibliotecaApp.missingBooksMessage + myApp.biblioteca.getBookList(false);
        String secondMessage = BibliotecaApp.selectBookToReturnMessage + BibliotecaApp.checkInUnsuccessfullyMessage;


        // Act
        myApp.userInteraction();

        // Verifying
        assertThat(byteArrayOutputStream.toString(), containsString(firstMessage + secondMessage));

        // Cleaning
        System.setIn(System.in);

    }
}
