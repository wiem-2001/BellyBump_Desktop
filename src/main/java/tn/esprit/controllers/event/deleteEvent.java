package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.controllers.coach.CoachController;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteEvent implements Initializable {
     private Event event;
     public void setEvent(Event event){
         this.event= event;
     }
    @FXML
    private Button canceldeleteBtn;

    @FXML
    private Button deletebtn;

    final EventService es= new EventService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deletebtn.setOnMouseClicked(actionEvent -> {

            try {
                es.delete(event);
                Scene scene = canceldeleteBtn.getScene();
                if (scene != null) {
                    // Get the stage of the scene and close it
                    Stage stage = (Stage) scene.getWindow();
                    stage.close();
                }

            } catch (Exception ex) {
                Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        canceldeleteBtn.setOnMouseClicked(e -> {
            // Get the scene of the cancel delete button
            Scene scene = canceldeleteBtn.getScene();
            if (scene != null) {
                // Get the stage of the scene and close it
                Stage stage = (Stage) scene.getWindow();
                stage.close();
            }
        });

    }
}
