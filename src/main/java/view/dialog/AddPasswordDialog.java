package view.dialog;

import controller.dialog.AddPasswordDialogController;
import encryption.EncryptionException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class AddPasswordDialog<CredentialsEntity> extends Dialog {

    public AddPasswordDialog(){
        Parent root = null;
        FXMLLoader loader = null;

        try {
            loader = new FXMLLoader(this.getClass().getResource(("/add_password_dialog.fxml")));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddPasswordDialogController controller = loader.getController();

        this.setResultConverter(b -> {
            if (((ButtonType)b).getButtonData() == ButtonBar.ButtonData.OK_DONE)
                try {
                    return new model.credentials.CredentialsEntity( controller.getPlaceValue(),
                                                                    controller.getUserNameValue(),
                                                                    controller.getPasswordValue(),
                                                                    controller.getMainPasswordValue(),
                                                                    controller.getNoteValue());
                } catch (EncryptionException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

            return null;
        });

        this.getDialogPane().setContent(root);
        this.setTitle("Add password");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
}
