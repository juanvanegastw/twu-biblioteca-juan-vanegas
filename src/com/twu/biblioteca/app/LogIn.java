package com.twu.biblioteca.app;

import com.twu.biblioteca.library.user.LibraryUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LogIn {

    HashMap<String, LibraryUser> users;
    public static String insertLibraryNumberMessage = "Please insert your Library Number\n";
    public static String insertPasswordMessage = "Please insert your password\n";
    public static String wrongLibraryNumberMessage = "Library Number Not Found\n";
    public static String wrongPasswordMessage = "Wrong password\n";
    public static String maxAttemptsMessage = "You have exceed the limit of attempts\n";

    private Integer maxAttemptsNumber = 3;
    private BufferedReader bufferedReader;

    public LogIn(BufferedReader bufferedReader){
        this.bufferedReader = (bufferedReader==null) ? new BufferedReader(new InputStreamReader(System.in)): bufferedReader;

        this.users = new HashMap<>();
    }

    public void addValidUser(LibraryUser libraryUser){
        this.users.put(libraryUser.getLibraryNumber(), libraryUser);
    }

    private void showInsertLibraryNumberMessage(){
        System.out.print(insertLibraryNumberMessage);
    }

    private void showInsertPasswordMessage(){
        System.out.print(insertPasswordMessage);
    }

    private void showWrongLibraryNumberMessage(){
        System.out.print(wrongLibraryNumberMessage);
    }

    private void showWrongPasswordMessage(){
        System.out.print(wrongPasswordMessage);
    }

    private void showMaxAttemptsMessage(){
        System.out.print(maxAttemptsMessage);
    }

    public LibraryUser startLogInInteraction(){
        Integer attemptNumber = 0;
        while (attemptNumber < maxAttemptsNumber)
        {
            attemptNumber ++;
            LibraryUser user = startLogInAttempt();
            if (user != null){
                return user;
            }
        }
        showMaxAttemptsMessage();
        return null;
    }

    private LibraryUser startLogInAttempt(){
        showInsertLibraryNumberMessage();
        String libraryNumber = Menu.readUserInput(this.bufferedReader);
        LibraryUser user = this.users.get(libraryNumber);
        if (user == null){
            showWrongLibraryNumberMessage();
        }
        else{
            showInsertPasswordMessage();
            String password = Menu.readUserInput(this.bufferedReader);
            if (!user.checkValidPassword(password)){
                showWrongPasswordMessage();
                return null;
            }
        }
        return user;
    }
}
