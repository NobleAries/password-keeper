package view;

import controller.LoginController;
import controller.MainController;
import controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.authentication.Authenticator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class View extends Application {

    private Authenticator authenticator;
    private Stage primaryStage;

    // TODO Choose name
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

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }


    public void startMain(Stage stage){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainController controller = loader.getController();
        controller.setView(this);
        controller.setAuthenticator(authenticator);

        startScene(stage, new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));

        // This method had to be invoked after the Scene was shown, because in other case it kept setting View and Authenticator to some ambiguous HomeController instance.
        controller.updateChildrenViewAndController();
    }

    public void startLogin(Stage stage){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/login.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoginController controller = loader.getController();
        controller.setView(this);
        controller.setAuthenticator(authenticator);

        startScene(stage, new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
    }

    private void startRegister(Stage stage){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/register.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RegisterController controller = loader.getController();
        controller.setView(this);
        controller.setAuthenticator(authenticator);

        startScene(stage, new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
    }

    private void startScene(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }
}
