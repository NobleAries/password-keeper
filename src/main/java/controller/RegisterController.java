package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import view.SafePasswordField;


public class RegisterController extends Controller {

    @FXML private SafePasswordField registerPasswordField;


    @FXML protected void handleRegisterButtonAction(ActionEvent event) {
        try {
            authenticator.setFirstPassword(registerPasswordField.getPassword());

        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        view.startLogin(view.getPrimaryStage());
    }
}
