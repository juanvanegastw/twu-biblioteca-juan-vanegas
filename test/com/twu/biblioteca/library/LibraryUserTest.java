package com.twu.biblioteca.library;

import com.twu.biblioteca.library.user.LibraryUser;
import com.twu.biblioteca.library.user.UserException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class LibraryUserTest {
    @Test(expected = UserException.class)
    public void shouldReturnExceptionWhenCreatingAUserWithAFormatWithoutHyphen() throws UserException{
        String invalidLibraryUser = "abcd";
        new LibraryUser(invalidLibraryUser, "password");

    }


    @Test(expected = UserException.class)
    public void shouldReturnExceptionWhenCreatingAUserWithAFormatWithLetters() throws UserException{
        String invalidLibraryUser = "abc-abcd";
        new LibraryUser(invalidLibraryUser, "password");

    }

    @Test(expected = UserException.class)
    public void shouldReturnExceptionWhenCreatingAUserWithAFormatWithTooManyNumbers() throws UserException{
        String invalidLibraryUser = "11111-1111111";
        new LibraryUser(invalidLibraryUser, "password");

    }

    @Test
    public void shouldReturnTrueWhenInsertingAValidPassword() throws UserException{
        String validPassword = "password";
        LibraryUser libraryUser = new LibraryUser("111-1111", validPassword);

        boolean isValidPassword = libraryUser.checkValidPassword(validPassword);

        assertThat(isValidPassword, is(true));

    }
}
