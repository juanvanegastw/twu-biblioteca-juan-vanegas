package com.twu.biblioteca.library.user;

public class LibraryUser {
    private String libraryNumber;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public LibraryUser(String libraryNumber, String password, String name, String email, String phoneNumber) throws UserException{
        if (!isValidLibraryNumber(libraryNumber)){
            throw new UserException();
        }
        this.libraryNumber = libraryNumber;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;

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

    /**
     * Para hacerlo más descriptivo puedes usar insertedPassword en lugar de testingPassword
     */
    public boolean checkValidPassword(String testingPassword){
        return testingPassword.equals(this.password);
    }

    public String getLibraryNumber(){
        return this.libraryNumber;
    }

    public String getUserInfo(){
        return String.join(" ", new String[]{"Name:", this.name, "Email:", this.email, "Phone Number:", this.phoneNumber});
    }
}
