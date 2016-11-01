package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            if(!passwordsCSV.exists())
                passwordsCSV.createNewFile();

            credentials = FXCollections.observableArrayList(csvPersistenceManager.loadCredentials());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        tableView.setItems(credentials);
    }

    @FXML
    protected void handleAddButtonAction(ActionEvent event){
        AddPasswordDialog addPasswordDialog = new AddPasswordDialog();
        Optional<CredentialsEntity> result = addPasswordDialog.showAndWait();

        if (result.isPresent()) {
            credentials.add(result.get());
        }
    }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event){
        CredentialsEntity credentialsEntity = tableView.getSelectionModel().getSelectedItem();
        credentials.remove(credentialsEntity);
    }
}
