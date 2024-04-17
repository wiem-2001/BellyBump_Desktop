package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    private static String loggedInUserEmail;
    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public static void setLoggedInUserEmail(String loggedInUserId) {
        MainFX.loggedInUserEmail = loggedInUserId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/loginUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("login"); // Use primaryStage instead of stage
        primaryStage.setScene(scene);   // Use primaryStage instead of stage
        primaryStage.show();
    }

}
