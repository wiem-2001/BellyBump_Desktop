package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Event;
import tn.esprit.services.EventParticipationService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventController {
    @FXML
    private Label eventDayTxt;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label eventNameTxt;

    @FXML
    private Text participantsNbr;


    public void setData(Event event){
        String uploadFolder = "C:/Users/Eya/Downloads/bellybumpImages/event/";
        String eventImage = event.getImage();
        File destFile = new File(uploadFolder, eventImage);
        Image image = new Image(destFile.toURI().toString());
        eventImageView.setImage(image);
        eventNameTxt.setText(event.getName());
        Date date= event.getDay();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy");
        String formattedDate = formatter.format(date);
        eventDayTxt.setText(formattedDate);
        final EventParticipationService eps = new EventParticipationService();
        int nbr = eps.getAllParticipatedUsers(event).size();
        participantsNbr.setText(String.valueOf(nbr));



    }
}
