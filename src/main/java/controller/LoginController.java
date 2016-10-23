package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import view.SafePasswordField;


public class LoginController extends Controller {


    @FXML private SafePasswordField loginPasswordField;
    @FXML private Text passwordValid;


    @FXML protected void handleLoginButtonAction(ActionEvent event) {
        try {
            if ( authenticator.isPasswordValid( loginPasswordField.getPassword() ) )
                view.startMain(view.getPrimaryStage());
            else
                passwordValid.setText("Password incorrect!");

        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            passwordValid.setText("Could not access database, please restart application.");
            e.printStackTrace();
        }
    }

}
