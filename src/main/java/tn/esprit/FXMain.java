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
        System.out.println("start");
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/event/TableOfEvents.fxml"));
        System.out.println("end");
        try{
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Coaches View");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            throw  new RuntimeException();

        }
    }
}
