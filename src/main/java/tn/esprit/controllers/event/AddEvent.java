
package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;

import java.sql.Date;
import java.sql.Time;

public class AddEvent {

    @FXML
    private DatePicker dayField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField meetingCodeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField startTimeField;

    private final EventService es= new EventService();

    @FXML
    void addEvent(ActionEvent event) {
        // Retrieve values from input fields
        String name = nameField.getText();
        String image = imageField.getText();
        String description = descriptionField.getText();
        String meetingCode = meetingCodeField.getText();
        Date day = Date.valueOf(dayField.getValue());
        Time startTime = Time.valueOf(startTimeField.getText());
        Time endTime = Time.valueOf(endTimeField.getText());
        // Create an instance of Event
        Event new_event = new Event(name, image, description, meetingCode, day, startTime, endTime, false, null);

        try{
            es.add(new_event);
        }catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


}
