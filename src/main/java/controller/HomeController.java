package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import view.dialog.AddPasswordDialog;


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
        AddPasswordDialog addPasswordDialog = new AddPasswordDialog(authenticator);
        Optional<Pair<Boolean, CredentialsEntity>> result = addPasswordDialog.showAndWait();

        while (result.isPresent()) {
            if(result.get().getKey()) {
                credentials.add(result.get().getValue());
                result = Optional.empty();
            }
            else {
                CredentialsEntity credentialsEntity = result.get().getValue();
                addPasswordDialog = new AddPasswordDialog(authenticator, credentialsEntity.getPlace(), credentialsEntity.getUsername(), credentialsEntity.getNote());
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


    private void saveCredentials(List credentials){
        try {
            csvPersistenceManager.saveCredentials(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
