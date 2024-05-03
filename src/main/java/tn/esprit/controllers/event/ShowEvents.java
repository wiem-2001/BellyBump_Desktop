package tn.esprit.controllers.event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.controllers.motherSideBarController;
import tn.esprit.entities.Event;
import tn.esprit.entities.User;
import tn.esprit.services.EventParticipationService;
import tn.esprit.services.EventRecommendationService;
import tn.esprit.services.EventService;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowEvents implements Initializable {
    @FXML
    private GridPane eventContainer;
    @FXML
    private HBox scrollBox;


    final EventService es = new EventService();
    final EventParticipationService eps =new EventParticipationService();

    final UserServices us = new UserServices();
    private EventRecommendationService ers = new EventRecommendationService();
    List<Event> events = es.getAllFutureEvents();


    List<Event> recommendedEvents = getRecommendedEvents();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try{

        int column=0;
        int row=1;
        //recommendedEvents = new ArrayList<>(getRecommendedEvents());

        for (Event event:recommendedEvents) {
               // System.out.println(event);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/event/event.fxml"));
                try{
                HBox cardBox = fxmlLoader.load();
                EventC eventController=fxmlLoader.getController();
                eventController.setData(event);
                scrollBox.getChildren().add(cardBox);
                cardBox.setOnMouseClicked((mouseEvent -> {
                        try{
                            FXMLLoader loader2= new FXMLLoader();
                            loader2.setLocation(getClass().getResource("/event/EventDetails.fxml"));
                            Parent root = loader2.load();
                            EventDetails detailsController = loader2.getController();
                            detailsController.initData(event);
                            detailsController.setEvent(event);
                            Stage stage= (Stage) cardBox.getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
                }catch (IOException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
        }


        for (Event event:events) {


            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/event/eventCard.fxml"));

            try {
                VBox eventBox=loader.load();

                EventController eventController=loader.getController();
                eventController.setData(event);

                if(column==3){
                    column=0;
                    ++row;
                }
                eventContainer.add(eventBox,column++, row);
                GridPane.setMargin(eventBox,new Insets(30));

                eventBox.setOnMouseClicked((mouseEvent -> {
                    try{
                        FXMLLoader loader2= new FXMLLoader();
                        loader2.setLocation(getClass().getResource("/event/EventDetails.fxml"));
                        Parent root = loader2.load();
                        EventDetails detailsController = loader2.getController();
                        detailsController.initData(event);
                        detailsController.setEvent(event);
                        Stage stage= (Stage) eventBox.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));

        } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
        }
        }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("initilize error is "+exception.getMessage());
        }

    }

/****************************************RECOMMENDATION**************************************************/

    public List<Event> getRecommendedEvents(){
        String userProfilesJson = getUserProfiles();
        //TODO get authentified user
        String userEmail= MainFX.getLoggedInUserEmail();
        User user=us.getOne(userEmail);
        List<Event> recommendedEvents = ers.recommendEvents(userProfilesJson,user);
        //System.out.println("recomended controller"+recommendedEvents);
        return recommendedEvents;
    }

    public String getUserProfiles(){
        Map<Integer,List<Integer>> eventsOfUser= new HashMap<>();
        List<User> users = us.getAll();

        for (User u: users) {
            List<Integer> eventIds = new ArrayList<>();
            List<Event> participatedEvents =eps.getAllParticipatedEvents(u);

            for (Event e:participatedEvents) {
                eventIds.add(e.getId());
            }
            eventsOfUser.put(u.getId(),eventIds);
        }
        return convertToJson(eventsOfUser);
    }

    private String convertToJson(Map<Integer, List<Integer>> eventsOfUser) {
        String convertion = "{";
        for (Map.Entry<Integer, List<Integer>> entry : eventsOfUser.entrySet()) {
            Integer userId = entry.getKey();
            List<Integer> eventIds = entry.getValue();
            convertion+=String.valueOf(userId)+":"+eventIds+",";

        }
        convertion+=String.valueOf(-1)+":[]}";

        return convertion;

    }


}
