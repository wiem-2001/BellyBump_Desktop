package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.controllers.motherSideBarController;
import tn.esprit.entities.Event;
import tn.esprit.entities.User;
import tn.esprit.services.EventParticipationService;
import tn.esprit.services.EventService;
import tn.esprit.services.UserServices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EventDetails implements Initializable {
    private Event event;
    public void setEvent(Event event){
        this.event=event;
    }
    @FXML
    private Button JoinEventBtn;
    @FXML
    private Button cancelParticipationbtn;
    @FXML
    private Text coachJobTxt;

    @FXML
    private Text coachNameTxt;

    @FXML
    private Text coachedByTxt;

    @FXML
    private Text dayTxt;

    @FXML
    private Text descriptionTxt;

    @FXML
    private Text endTimeTxt;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label nameTxt;

    @FXML
    private Text startTimeTxt;
    @FXML
    private Text jobIsTxt;
    @FXML
    private VBox sidebar;

    EventParticipationService eps = new EventParticipationService();
    UserServices us= new UserServices();
    User user =us.getUser(73);//TODO : get authentfied user

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/motherSideBar.fxml"));
        try{
            VBox sideBar = fxmlLoader1.load();
            motherSideBarController eventController=fxmlLoader1.getController();
            sidebar.getChildren().add(sideBar);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    public void initData(Event event){
        this.event=event;
        String uploadFolder = "C:/Users/Eya/Downloads/bellybumpImages/event/";
        String eventImage = event.getImage();
        File destFile = new File(uploadFolder, eventImage);
        Image image = new Image(destFile.toURI().toString());
        eventImageView.setImage(image);
        nameTxt.setText(event.getName()+"Event");
        descriptionTxt.setText(event.getDescription());
        Date date= event.getDay();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy");
        String formattedDate = formatter.format(date);
        dayTxt.setText("The "+formattedDate);
        startTimeTxt.setText(event.getHeureDebut().toString());
        endTimeTxt.setText(event.getHeureFin().toString());
        if (event.getCoach()!=null){
            coachNameTxt.setText(event.getCoach().getFirstname()+" "+event.getCoach().getLastname());
            coachJobTxt.setText(event.getCoach().getJob());
        }
        else{
            coachJobTxt.setText("");
            coachNameTxt.setText("");
            coachedByTxt.setText("");
            jobIsTxt.setText("");
        }

        boolean existe= false;
        for(Event e:eps.getAllParticipatedEvents(user)){
            if (e.getId() == event.getId()){
                existe=true;
                break;
            }
        }
        if(existe){
            JoinEventBtn.setVisible(false);
            cancelParticipationbtn.setVisible(true);
        }
        else {
            cancelParticipationbtn.setVisible(false);
            JoinEventBtn.setVisible(true);
        }
        JoinEvent();
        CancelParticipation();

    }


    void JoinEvent() {

        JoinEventBtn.setOnMouseClicked(ev ->{
            try{
                    eps.add(event, user);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("participéé");
                    alert.setContentText("joined");
                    alert.showAndWait();
                    initData(event);

            }catch (Exception e){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();



            }
        });


    }

    void CancelParticipation() {

        cancelParticipationbtn.setOnMouseClicked(ev ->{
            try{
                eps.delete(event, user);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("participation Canceled");
                alert.setContentText("Canceled");
                alert.showAndWait();
                initData(event);

            }catch (Exception e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();


            }
        });


    }




}
