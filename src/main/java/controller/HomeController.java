package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController extends Controller{


    @FXML
    protected void handleAddButtonAction(ActionEvent event){
        System.out.println("Add button clicked");
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){
        System.out.println("Delete button clicked");
    }
}
