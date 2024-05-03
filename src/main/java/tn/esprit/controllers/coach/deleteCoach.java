package tn.esprit.controllers.coach;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteCoach implements Initializable {
    private Coach coach;
    public void setCoach(Coach c){
        this.coach= c;
    }
    @FXML
    private Button canceldeleteBtn;

    @FXML
    private Button deletebtn;

    final CoachService cs= new CoachService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deletebtn.setOnMouseClicked(actionEvent -> {

            try {
                cs.delete(coach);
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
