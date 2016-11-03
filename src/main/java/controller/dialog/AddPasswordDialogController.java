package controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.SafePasswordField;

public class AddPasswordDialogController {

    @FXML private TextField place;
    @FXML private TextField userName;
    @FXML private SafePasswordField password;
    @FXML private SafePasswordField mainPassword;
    @FXML private TextField note;

    public String getPlaceValue(){
        return place.getText();
    }

    public String getUserNameValue(){
        return userName.getText();
    }

    public char [] getPasswordValue() throws NoSuchFieldException, IllegalAccessException {
        return password.getPassword();
    }

    public char [] getMainPasswordValue() throws NoSuchFieldException, IllegalAccessException {
        return mainPassword.getPassword();
    }

    public String getNoteValue(){
        return note.getText();
    }
}
