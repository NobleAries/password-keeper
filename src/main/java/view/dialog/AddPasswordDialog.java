package view.dialog;

import controller.dialog.AddPasswordDialogController;
import encryption.EncryptionException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;


import java.io.IOException;
import model.authentication.Authenticator;
import model.credentials.CredentialsEntity;

public class AddPasswordDialog<Pair> extends Dialog {

    private AddPasswordDialogController controller;

    public AddPasswordDialog(Authenticator authenticator){
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = new FXMLLoader(this.getClass().getResource(("/add_password_dialog.fxml")));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = loader.getController();

        this.setResultConverter(b -> {
            if (((ButtonType)b).getButtonData() == ButtonBar.ButtonData.OK_DONE)
                try {
                    Boolean passwordValid = authenticator.isPasswordValid(controller.getMainPasswordValue());

                    return new javafx.util.Pair<>(passwordValid, new CredentialsEntity( controller.getPlaceValue(),
                                                                                        controller.getUserNameValue(),
                                                                                        controller.getPasswordValue(),
                                                                                        controller.getMainPasswordValue(),
                                                                                        controller.getNoteValue()));
                } catch (EncryptionException | IllegalAccessException | NoSuchFieldException | IOException e) {
                    e.printStackTrace();
                }

            return null;
        });

        this.getDialogPane().setContent(root);
        this.setTitle("Add password");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    public AddPasswordDialog(Authenticator authenticator, String place, String userName, String note){
        this(authenticator);
        setFieldValues(place, userName, note);
        controller.setMessageValue("Main password incorrect.");
    }


    private void setFieldValues(String place, String userName, String note){
        controller.setPlaceValue(place);
        controller.setUserNameValue(userName);
        controller.setNoteValue(note);
    }
}
