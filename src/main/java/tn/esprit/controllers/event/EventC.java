package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tn.esprit.entities.Event;
import tn.esprit.services.EventParticipationService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventC {
    @FXML
    private Text eventDayTxt;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Text eventNameTxt;

    @FXML
    private Text participantsNbr;

    @FXML
    private HBox cardE;
    final EventParticipationService eps = new EventParticipationService();
    String[] colors = {"#ea92b5", "#8eb3c7","#ad8fc7","#c78fab"};
    public void setData(Event event){
        //todo to change in every computer
        String uploadFolder = "C:/Users/Eya/Downloads/bellybumpImages/event/";
        String eventImage = event.getImage();
        File destFile = new File(uploadFolder, eventImage);
        Image image = new Image(destFile.toURI().toString());
        eventImageView.setImage(image);
        eventNameTxt.setText(event.getName());
        Date date= event.getDay();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy");
        String formattedDate = formatter.format(date);
        eventDayTxt.setText("The "+formattedDate);
        participantsNbr.setText(eps.getAllParticipatedUsers(event).size()+" participant(s)");
        String randomColor = colors[(int) (Math.random()*colors.length)];
        cardE.setStyle("-fx-background-color: rgba(206,92,137,0.89)");
        cardE.setStyle("-fx-background-color: "+ randomColor);

    }
}
