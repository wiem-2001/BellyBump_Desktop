package tn.esprit.Controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.Controllers.coach.CoachController;
import tn.esprit.Controllers.motherSideBarController;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.entities.User;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventParticipationService;
import tn.esprit.services.EventService;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

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
    private Text meetingCode;

    @FXML
    private Text meetingCodeTXT;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label nameTxt;

    @FXML
    private Text startTimeTxt;
    @FXML
    private Text jobIsTxt;
    @FXML
    private ImageView backToEventList;

    EventParticipationService eps = new EventParticipationService();
    UserServices us= new UserServices();
    String userEmail= MainFX.getLoggedInUserEmail();
    User user=us.getOne(userEmail);//TODO : get authentfied user

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Event event){
        this.event=event;
        //TODO change in every machine
        String uploadFolder = "C:/Users/eya/Desktop/BellyBumpImages";//"C:/Users/user/Downloads/bellybumpImages/event";//
        String eventImage = event.getImage();
        File destFile = new File(uploadFolder, eventImage);
        Image image = new Image(destFile.toURI().toString());
        eventImageView.setImage(image);
        nameTxt.setText(event.getName()+" Event");
        descriptionTxt.setText(event.getDescription());
        Date date= event.getDay();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy");
        String formattedDate = formatter.format(date);
        dayTxt.setText("The "+formattedDate);
        startTimeTxt.setText(event.getHeureDebut().toString());
        endTimeTxt.setText(event.getHeureFin().toString());
        boolean existe= false;


        if (event.getCoach()!=-1){
            final CoachService cs = new CoachService();
            Coach coach= cs .getOne(event.getCoach());
            coachNameTxt.setText(coach.getFirstname()+" "+coach.getLastname());
            coachJobTxt.setText(coach.getJob());
        }
        else{
            coachJobTxt.setText("");
            coachNameTxt.setText("");
            coachedByTxt.setText("");
            jobIsTxt.setText("");
        }


        for(Event e:eps.getAllParticipatedEvents(user)){
            if (e.getId() == event.getId()){
                existe=true;
                break;
            }
        }

        if(existe){
            JoinEventBtn.setVisible(false);
            cancelParticipationbtn.setVisible(true);
            meetingCode.setText(event.getMeetingCode());
            meetingCodeTXT.setVisible(true);
        }
        else {
            cancelParticipationbtn.setVisible(false);
            JoinEventBtn.setVisible(true);
            meetingCode.setText("");
            meetingCodeTXT.setVisible(false);
        }

        JoinEvent();
        CancelParticipation();
        backToEventList.setOnMouseClicked(event1 ->{


            FXMLLoader loader2= new FXMLLoader();
            loader2.setLocation(getClass().getResource("/motherSideBar.fxml"));
            Parent root = null;
            try {
                root = loader2.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            motherSideBarController controller = loader2.getController();
            controller.setEventList();
            Stage stage= (Stage) backToEventList.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

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
