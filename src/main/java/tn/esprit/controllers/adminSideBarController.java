package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.controllers.coach.CoachController;
import tn.esprit.controllers.event.AdminEventsList;
import tn.esprit.controllers.event.ShowEvents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminSideBarController implements Initializable {

    @FXML
    private HBox coachTableViewClick;
    @FXML
    private HBox logoutLinkOnClick;

    @FXML
    private HBox manageUsersOnClick;

    @FXML
    private HBox eventTableViewClick;
    @FXML
    Text userEmailT;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
        eventTableViewClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/TableOfEvents.fxml"));
                Parent root = loader2.load();
                AdminEventsList showEvents = loader2.getController();
                Stage stage= (Stage) eventTableViewClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        coachTableViewClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/coach/coachTableView.fxml"));
                Parent root = loader2.load();
                CoachController showEvents = loader2.getController();
                Stage stage= (Stage) coachTableViewClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        logoutLinkOnClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/loginUI.fxml"));
                Parent root = loader2.load();
                AdminEventsList showEvents = loader2.getController();
                Stage stage= (Stage) eventTableViewClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        manageUsersOnClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/usersDashboard.fxml"));
                Parent root = loader2.load();
                AdminEventsList showEvents = loader2.getController();
                Stage stage= (Stage) eventTableViewClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }
}
