package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.util.Pair;
import persistence.CsvPersistenceManager;
import model.credentials.CredentialsEntity;
import view.dialog.EntityDialog;


public class HomeController extends Controller{

    @FXML private TableView<CredentialsEntity> tableView;

    private File passwordsCSV = new File("passwords.csv");
    private CsvPersistenceManager csvPersistenceManager = new CsvPersistenceManager(passwordsCSV);
    private ObservableList<CredentialsEntity> credentials;


    public HomeController(){
        try {
            credentials = FXCollections.observableArrayList(csvPersistenceManager.loadCredentials());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(credentials);
    }

    @FXML
    protected void handleAddButtonAction(ActionEvent event){
        EntityDialog addPasswordDialog = new EntityDialog(authenticator, EntityDialog.ADD_PASSWORD_TITLE, "");
        Optional<Pair<Boolean, CredentialsEntity>> result = addPasswordDialog.showAndWait();

        while (result.isPresent()) {
            if(result.get().getKey()) {
                credentials.add(result.get().getValue());
                result = Optional.empty();
            }
            else {
                CredentialsEntity credentialsEntity = result.get().getValue();
                addPasswordDialog = new EntityDialog(   authenticator,
                                                        credentialsEntity.getPlace(),
                                                        credentialsEntity.getUsername(),
                                                        credentialsEntity.getNote(),
                                                        EntityDialog.ADD_PASSWORD_TITLE,
                                                        "Main password incorrect");
                result = addPasswordDialog.showAndWait();
            }
            saveCredentials(credentials);
        }
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){
        ObservableList<CredentialsEntity> credentialsEntities = tableView.getSelectionModel().getSelectedItems();
        credentials.removeAll(credentialsEntities);
        saveCredentials(credentials);
    }

    @FXML
    protected void handleEditButtonAction(ActionEvent event){
        ObservableList<CredentialsEntity> credentialsEntities = tableView.getSelectionModel().getSelectedItems();

        if(credentialsEntities.size() == 1){
            CredentialsEntity currentEntity = credentialsEntities.get(0);
            EntityDialog entityDialog = new EntityDialog(  authenticator,
                                                                currentEntity.getPlace(),
                                                                currentEntity.getUsername(),
                                                                currentEntity.getNote(),
                                                                EntityDialog.EDIT_PASSWORD_TITLE,
                                                                "");
            Optional<Pair<Boolean, CredentialsEntity>> result = entityDialog.showAndWait();

            while (result.isPresent()) {
                if(result.get().getKey()) {
                    credentials.set(credentials.indexOf(currentEntity), result.get().getValue());
                    break;
                }
                else {
                    CredentialsEntity credentialsEntity = result.get().getValue();
                    entityDialog = new EntityDialog(   authenticator,
                                                            credentialsEntity.getPlace(),
                                                            credentialsEntity.getUsername(),
                                                            credentialsEntity.getNote(),
                                                            EntityDialog.EDIT_PASSWORD_TITLE,
                                                            "Main password incorrect");
                    result = entityDialog.showAndWait();
                }
                saveCredentials(credentials);
            }
        }
        else{
            // TODO Add dialog with information that user can edit precisely one item at a time.
        }
    }


    private void saveCredentials(List credentials){
        try {
            csvPersistenceManager.saveCredentials(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
