package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Shop.fxml"));
            primaryStage.setTitle("Ajouter Partenaire");
            primaryStage.setScene(new Scene(root, 986, 600));
            primaryStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
