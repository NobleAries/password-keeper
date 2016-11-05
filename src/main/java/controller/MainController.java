package controller;

import javafx.fxml.FXML;

public class MainController extends Controller{

    @FXML private HomeController homeController;


    public void updateChildrenViewAndController(){
        homeController.setView(view);
        homeController.setAuthenticator(authenticator);
    }

}