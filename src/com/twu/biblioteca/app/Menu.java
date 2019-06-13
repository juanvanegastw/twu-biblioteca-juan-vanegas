package com.twu.biblioteca.app;

import com.twu.biblioteca.library.item.RentalItemService;
import com.twu.biblioteca.library.user.LibraryUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static  com.twu.biblioteca.app.MenuMessages.*;

class Menu {

    private BufferedReader bufferedReader;
    private MenuService bookMenuService;
    private MenuService movieMenuService;
    private LogIn logIn;
    private LibraryUser user;

    /**
     * BibliotecaApp siempre envía null en el parámetro de BufferReacer ¿Por qué el constructor lo recibe?
     */
    public Menu (RentalItemService rentBookService, RentalItemService rentMovieService, BufferedReader reader){
        this.bufferedReader = (reader==null) ? new BufferedReader(new InputStreamReader(System.in)): reader;
        this.bookMenuService = new MenuService(this, "book", rentBookService);
        this.movieMenuService = new MenuService(this, "movie", rentMovieService);
        this.logIn = null;
    }

    public void setLogIn(LogIn logIn){
        this.logIn = logIn;
    }


    private void showLoggedUserInfo(){
        System.out.print(userInfoMessage + this.user.getUserInfo() + "\n");
    }

    private void askForLogInIfUserIsNotLogged(){
        if (this.user == null){
            this.user = getLibraryUserFromUser();
        }
    }
    
    protected boolean executeUserSelectedOption(String option){
        boolean programMustContinue = true;
        switch (option){
            case "1":
                this.bookMenuService.showTheListOfRentItemService(true);
                break;
            case "2":
                this.movieMenuService.showTheListOfRentItemService(true);
                break;
            case "q":
                MenuMessages.showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                askForLogInIfUserIsNotLogged();
                if(this.user != null) {
                    this.bookMenuService.startUserInteractionToCheckOut(this.user);
                }
                break;
            case "4":
                askForLogInIfUserIsNotLogged();
                if(this.user != null) {
                    this.bookMenuService.startUserInteractionToCheckIn();
                }
                break;
            case "5":
                this.movieMenuService.startUserInteractionToCheckOut(this.user);
                break;
            case "6":
                this.movieMenuService.startUserInteractionToCheckIn();
                break;
            case "l":
                this.user = null;
            case "p":
                if(this.user == null){
                    showInvalidOptionMessage();
                }
                else {
                    showLoggedUserInfo();
                }
                break;
            default:
                showInvalidOptionMessage();
        }
        return programMustContinue;
    }

    protected static String readUserInput(BufferedReader bufferedReader){
        try {
            return bufferedReader.readLine();
        }
        catch (Exception exception){
            showGeneralExceptionMessage(exception);
            return "";
        }
    }

    public String readOptionSelected(){
        return readUserInput(this.bufferedReader);
    }

    private LibraryUser getLibraryUserFromUser(){
        if (logIn == null){
            return null;
        }
        return logIn.startLogInInteraction();
    }

    private void startMenuServices(){
        MenuMessages.showGeneralMenu(this.user);
        boolean programMustContinue = executeUserSelectedOption(readOptionSelected());
        if (programMustContinue)
            startMenuServices();
    }

    public void startLibrary(){
        MenuMessages.showWelcomeMessage();
        startMenuServices();
    }

}
