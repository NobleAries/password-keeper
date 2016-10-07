package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import model.authentication.Authenticator;

import view.View;

public class Controller {

    private View view;
    private Authenticator authenticator;

    @FXML private PasswordField registerPasswordField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Text passwordValid;


    public void setModel(Authenticator authenticator){
        this.authenticator = authenticator;
    }

    public void setView(View view){
        this.view = view;
    }

    @FXML protected void handleLoginButtonAction(ActionEvent event) {
        try {
            if ( authenticator.isPasswordValid( loginPasswordField.getText() ) )
                view.startMain(view.getPrimaryStage());
            else
                passwordValid.setText("Password incorrect!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleRegisterButtonAction(ActionEvent event) {
        try {
            authenticator.setFirstPassword(registerPasswordField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.startLogin(view.getPrimaryStage());
    }
}
