package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class EventController implements Initializable {
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    private Label eventDayTxt;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label eventNameTxt;

    @FXML
    private Text participantsNbr;

    @FXML
    private Button eventCardBtn;

    public void setData(Event event){
        setEvent(event);
        //TODO change in every machine
        String uploadFolder = "C:/Users/Eya/Downloads/bellybumpImages/event/"; //"C:/Users/user/Downloads/bellybumpImages/event";//
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventCardBtn.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/EventDetails.fxml"));
                Parent root = loader2.load();
                EventDetails detailsController = loader2.getController();
                detailsController.initData(event);
                detailsController.setEvent(event);
                Stage stage= (Stage) eventCardBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
