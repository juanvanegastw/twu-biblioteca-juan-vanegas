package com.twu.biblioteca.app;

import com.twu.biblioteca.library.rent.RentItemService;
import com.twu.biblioteca.library.user.LibraryUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Para que tu código sea más legible podrías agrupar los fields y separarlos de los métodos.
 */
class Menu {

    private BufferedReader bufferedReader;
    private MenuService bookMenuService;
    private MenuService movieMenuService;
    private LogIn logIn;
    private LibraryUser user;

    /**
     * ¿Por qué estos fields y métodos deben ser protected?
     */
    protected static String welcomeMessage = "Welcome to Biblioteca. You one-stop-shop for great book titles in Bangalore.\n\n";

    protected static String userInfoOption = "Insert \"p\" to see the user profile \n";

    protected static String userInfoMessage = "Your data is:\n";

    private String getMenu(){
        if(this.user != null){
            return generalMenu + userInfoOption + selectOptionOption;
        }
        else
        {
            return generalMenu + selectOptionOption;
        }
    }

    protected static String generalMenu = "\nSelect an option\n" +
            "Insert \"1\" to show book list \n" +
            "Insert \"2\" to show movie list \n" +
            "Insert \"3\" to check out a book \n" +
            "Insert \"4\" to return a book\n" +
            "Insert \"5\" to check out a movie \n" +
            "Insert \"6\" to return a movie\n" +
            "Insert \"l\" to log out \n" +
            "Insert \"q\" to quit \n";
    /**
     * Esta duplicado "option" en el nombre
     */
    protected static String selectOptionOption = "Insert and press Enter:\n";

    protected static String leavingMessage = "Thanks for using Biblioteca";
    protected static String invalidOptionMessage = "Please select a valid option!\n";
    protected static String generalExceptionMessage = "A error has happened and has thrown the next Exception:\n";


    /**
     * ¿Por qué el constructor es protected?
     * BibliotecaApp siempre envía null en el parámetro de BufferReacer ¿Por qué el constructor lo recibe?
     */
    protected Menu (RentItemService rentBookService, RentItemService rentMovieService, BufferedReader reader){
        this.bufferedReader = (reader==null) ? new BufferedReader(new InputStreamReader(System.in)): reader;
        this.bookMenuService = new MenuService(this, "book", rentBookService);
        this.movieMenuService = new MenuService(this, "movie", rentMovieService);
        this.logIn = null;
    }

    public void setLogIn(LogIn logIn){
        this.logIn = logIn;
    }

    /**
     * Este método es protected, pero se usa sólo en esta clase ¿Cuál sería el modificador de acceso correcto?
     */
    protected void showGeneralMenu(){
        System.out.print(getMenu());
    }

    private void showInvalidOptionMessage(){
        System.out.print(invalidOptionMessage);
    }

    private void showLoggedUserInfo(){
        System.out.print(userInfoMessage + this.user.getUserInfo() + "\n");
    }

    /**
     * Este método está muy largo, ¿cómo podrías reducir su tamaño?, ¿Crees que existe código duplicado?
     */
    protected boolean executeUserSelectedOption(String option){
        boolean programMustContinue = true;
        switch (option){
            case "1":
                this.bookMenuService.showTheListOfItems(true);
                break;
            case "2":
                this.movieMenuService.showTheListOfItems(true);
                break;
            case "q":
                showLeavingMessage();
                programMustContinue = false;
                break;
            case "3":
                if (this.user == null){
                    this.user = getLibraryUserFromUser();
                }
                if(this.user != null) {
                    this.bookMenuService.startUserInteractionToCheckOut(this.user);
                }
                break;
            case "4":
                if (this.user == null){
                    this.user = getLibraryUserFromUser();
                }
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

    private void showLeavingMessage(){
        System.out.print(leavingMessage);
    }

    /**
     * ¿Cuál es el modificar de acceso de este método?
     */
    static String readUserInput(BufferedReader bufferedReader){
        try {
            return bufferedReader.readLine();
        }
        catch (Exception exception){
            showGeneralExceptionMessage(exception);
            return "";
        }
    }

    /**
     * Este método es protected, pero se usa en MenuService ¿Cuál sería el modificador de acceso correcto?
     */
    protected String readOptionSelected(){
        return readUserInput(this.bufferedReader);
    }

    private LibraryUser getLibraryUserFromUser(){
        if (logIn == null){
            return null;
        }
        return logIn.startLogInInteraction();
    }

    /**
     * Este método es protected, pero se usa solo es esta clase ¿Cuál sería el modificador de acceso correcto?
     */
    protected void startMenuServices(){
        showGeneralMenu();
        boolean programMustContinue = executeUserSelectedOption(readOptionSelected());
        if (programMustContinue)
            startMenuServices();
    }

    public void startLibrary(){
        showWelcomeMessage();
        startMenuServices();
    }

    /**
     * Este método es protected, pero se usa solo es esta clase ¿Cuál sería el modificador de acceso correcto?
     */
    protected void showWelcomeMessage(){
        System.out.print(welcomeMessage);
    }

    /**
     * ¿Cuál es el módificar de acceso de este método?
     */
    static void showGeneralExceptionMessage(Exception exception){
        System.out.print(generalExceptionMessage + exception.toString());
    }
}
