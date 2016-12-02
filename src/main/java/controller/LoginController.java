package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import view.SafePasswordField;


public class LoginController extends Controller {


    @FXML private SafePasswordField loginPasswordField;
    @FXML private Text passwordValid;


    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        try {
            if ( authenticator.isPasswordValid( loginPasswordField.getPassword() ) )
                view.startMain(view.getPrimaryStage());
            else
                passwordValid.setText("Password incorrect!");

        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was a serious error while accessing database.");
            alert.setContentText("The application will stop.");
            alert.showAndWait();

            e.printStackTrace();

            Platform.exit();
        }
    }

}
