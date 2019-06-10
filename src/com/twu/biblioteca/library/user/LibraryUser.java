package com.twu.biblioteca.library.user;

public class LibraryUser {
    private String libraryNumber;
    private String password;
    public LibraryUser(String libraryNumber, String password) throws UserException{
        if (!isValidLibraryNumber(libraryNumber)){
            throw new UserException();
        }
        this.libraryNumber = libraryNumber;
        this.password = password;

    }
    private boolean isValidLibraryNumber(String libraryNumber){
        String[] libraryNumberArray = libraryNumber.split("-");
        if (libraryNumberArray.length != 2 || libraryNumberArray[0].length() != 3 || libraryNumberArray[1].length() != 4){
            return false;
        }
        try
        {
            Integer.parseInt(libraryNumberArray[0]);
            Integer.parseInt(libraryNumberArray[1]);
        }
        catch (Exception exception){
            return false;
        }
        return true;
    }

    public boolean checkValidPassword(String testingPassword){
        return testingPassword.equals(this.password);
    }

    public String getLibraryNumber(){
        return this.libraryNumber;
    }

}
