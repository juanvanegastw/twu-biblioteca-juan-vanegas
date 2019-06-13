package com.twu.biblioteca.app;

import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;


public class LogInTest {
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Before
    public void setUp(){
        System.setOut(new PrintStream(this.byteArrayOutputStream));
    }

    private void addLogInUsers(LogIn logIn) throws UserException{
        LibraryUser firstLibraryUser = new LibraryUser("111-1111", "password1", "name", "email", "094");
        logIn.addValidUser(firstLibraryUser);
        LibraryUser secondLibraryUser = new LibraryUser("222-2222", "password2", "name", "email", "094");
        logIn.addValidUser(secondLibraryUser);
        LibraryUser thirdLibraryUser = new LibraryUser("333-3333", "password3", "name", "email", "094");
        logIn.addValidUser(thirdLibraryUser);
    }

    private LogIn setUpOneLogInUserAndExit() throws IOException, UserException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("111-1111").thenReturn("password1").thenReturn("q");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);
        return logIn;
    }
    @Test
    public void shouldAskMeForUserNumberWhenCallingStartLogInInteraction() throws IOException, UserException{
        LogIn logIn = setUpOneLogInUserAndExit();

        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.insertLibraryNumberMessage));
    }

    @Test
    public void shouldAskMeForPasswordAfterInsertingLibraryNumberWhenCallingStartLogInInteraction() throws IOException, UserException{
        LogIn logIn = setUpOneLogInUserAndExit();
        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.insertPasswordMessage));
    }

    @Test
    public void shouldCallTwiceTheFunctionReadLinewhenCallingStartLogInInteraction() throws IOException, UserException{
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("111-1111").thenReturn("password1");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);

        logIn.startLogInInteraction();

        verify(bufferedReader, times(2)).readLine();
    }

    @Test
    public void shouldShow_userNotFoundMessage_whenInsertingANotExistingUser() throws IOException, UserException{
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("555-5555").thenReturn("111-1111").thenReturn("password1");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);

        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.wrongLibraryNumberMessage));
    }

    @Test
    public void shouldShow_wrongPasswordMessage_whenInsertingAWrongPassword() throws IOException, UserException{
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("111-1111").thenReturn("password2").thenReturn("111-1111").thenReturn("password1");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);

        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.wrongPasswordMessage));
    }

    @Test
    public void shouldShow_maxTriesMessage_whenInsertingAWrongLibraryNumber() throws IOException, UserException{
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("abcd").thenReturn("abcd").thenReturn("abcd");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);

        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.maxAttemptsMessage));
    }

    @Test
    public void shouldShow_maxTriesMessage_whenInsertingAWrongPasswordThreeTimes() throws IOException, UserException{
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("111-1111").thenReturn("wrong").thenReturn("111-1111").thenReturn("wrong").thenReturn("111-1111").thenReturn("wrong");
        LogIn logIn = new LogIn(bufferedReader);
        addLogInUsers(logIn);

        logIn.startLogInInteraction();

        assertThat(this.byteArrayOutputStream.toString(), containsString(LogIn.maxAttemptsMessage));
    }
}
