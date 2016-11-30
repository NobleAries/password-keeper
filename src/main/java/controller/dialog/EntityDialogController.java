package controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.SafePasswordField;

public class EntityDialogController {

    @FXML private TextField place;
    @FXML private TextField userName;
    @FXML private SafePasswordField password;
    @FXML private SafePasswordField mainPassword;
    @FXML private TextField note;
    @FXML private Text message;

    public String getPlaceValue(){
        return place.getText();
    }

    public void setPlaceValue(String value){ place.setText(value);}

    public String getUserNameValue(){
        return userName.getText();
    }

    public void setUserNameValue(String value){ userName.setText(value);}

    public char [] getPasswordValue() throws NoSuchFieldException, IllegalAccessException {
        return password.getPassword();
    }

    public char [] getMainPasswordValue() throws NoSuchFieldException, IllegalAccessException {
        return mainPassword.getPassword();
    }

    public String getNoteValue(){
        return note.getText();
    }

    public void setNoteValue(String value){ note.setText(value);}

    public void setMessageValue(String content){
        message.setText(content);
    }
}
