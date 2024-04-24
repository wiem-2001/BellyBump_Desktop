package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.controllers.event.EventDetails;
import tn.esprit.controllers.event.ShowEvents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class motherSideBarController implements Initializable {
    @FXML
    private HBox calendarClick;

    @FXML
    private HBox eventListClick;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventListClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/showEvents.fxml"));
                Parent root = loader2.load();
                ShowEvents showEvents = loader2.getController();
                Stage stage= (Stage) eventListClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        calendarClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/CalendarFile.fxml"));
                Parent root = loader2.load();
                Stage stage= (Stage) eventListClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
