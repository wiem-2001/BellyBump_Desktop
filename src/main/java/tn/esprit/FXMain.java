package tn.esprit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.Main;

import java.io.IOException;

public class FXMain extends Application {
    private static String loggedInUserEmail;
    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public static void setLoggedInUserEmail(String loggedInUserId) {
        FXMain.loggedInUserEmail = loggedInUserId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/loginUI.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/AjouterBaby.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

