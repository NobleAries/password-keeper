package view.dialog;

import controller.dialog.EntityDialogController;
import encryption.EncryptionException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import java.io.IOException;

import model.authentication.Authenticator;
import model.credentials.CredentialsEntity;


public class EntityDialog<Pair> extends Dialog {

    public final static String ADD_PASSWORD_TITLE = "Add password";
    public final static String EDIT_PASSWORD_TITLE = "Edit password";

    private EntityDialogController controller;

    public EntityDialog(Authenticator authenticator, String title, String message){
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = new FXMLLoader(this.getClass().getResource(("/entity_dialog.fxml")));
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
        this.setTitle(title);
        controller.setMessageValue(message);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    public EntityDialog(Authenticator authenticator, String place, String userName, String note, String title, String message){
        this(authenticator, title, message);
        setFieldValues(place, userName, note);
    }


    private void setFieldValues(String place, String userName, String note){
        controller.setPlaceValue(place);
        controller.setUserNameValue(userName);
        controller.setNoteValue(note);
    }
}
