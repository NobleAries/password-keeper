package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;
import model.authentication.Authenticator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class View extends Application {

    private Authenticator authenticator;
    private Stage primaryStage;

    // TODO
    private Path path = Paths.get("samplename.txt");
    private static final int STAGE_WIDTH = 600;
    private static final int STAGE_HEIGHT = 400;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            authenticator = new Authenticator(path);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        primaryStage.setTitle("Password Keeper");

        if( !authenticator.isPasswordSet() )
            startRegister(primaryStage);
        else
            startLogin(primaryStage);
    }

    /**
     * Retrieves app's main scene and then switches to it.
     * @param stage Primary stage of the app
     */
    public void startMain(Stage stage){
        startScene(stage, retrieveScene("/main.fxml"));
    }

    /**
     * Retrieves app's login scene and then switches to it.
     * @param stage Primary stage of the app
     */
    public void startLogin(Stage stage){
        startScene(stage, retrieveScene("/login.fxml"));
    }

    private void startRegister(Stage stage){
        startScene(stage, retrieveScene("/register.fxml"));
    }

    private void startScene(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method loads scene from provided resource file. Next it retrieves the resource's controller and sets Model and View to it.
     * @param pathToResource Absolute or relative path to fxml file as String, since getResource method accepts only Strings.
     * @return Scene loaded from provided resource file.
     */
    private Scene retrieveScene(String pathToResource){
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(this.getClass().getResource((pathToResource)));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();
        controller.setView(this);
        controller.setModel(authenticator);

        return new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
