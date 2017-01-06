package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import javafx.scene.control.Alert;
import view.SafePasswordField;


public class RegisterController extends Controller {

    @FXML private SafePasswordField registerPasswordField;


    @FXML
    protected void handleRegisterButtonAction(ActionEvent event) {
        try {
            authenticator.setFirstPassword(registerPasswordField.getPassword());

        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was a serious error while creating the password.");
            alert.setContentText("The application will stop.");
            alert.showAndWait();

            e.printStackTrace();

            Platform.exit();
        }
        view.startLogin(view.getPrimaryStage());
    }
}
