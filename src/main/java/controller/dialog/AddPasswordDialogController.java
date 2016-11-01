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

    public char [] getPasswordValue(){
        try {
            return password.getPassword();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public char [] getMainPasswordValue(){
        try {
            return mainPassword.getPassword();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNoteValue(){
        return note.getText();
    }
}
