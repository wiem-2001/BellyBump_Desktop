package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;

import java.util.List;

public class AdminEventsList {

    @FXML
    private TableColumn<Event, String> EndTCol;

    @FXML
    private TableView<Event> EventTable;
/*
    @FXML
    private TableColumn<Event, ?> actionBtnsCol;
*/
    @FXML
    private TableColumn<Event, String> coachCol;

    @FXML
    private TableColumn<Event, String> codeCol;

    @FXML
    private TableColumn<Event, String> dayCol;

    @FXML
    private TableColumn<Event, String> descriptionCol;

    @FXML
    private TableColumn<Event, String> nameCol;

    @FXML
    private TableColumn<Event, String> startTCol;

    private final EventService es= new EventService();
    private final CoachService cs= new CoachService();

    @FXML
    void initialize(){
        try{
            List<Event> events= es.getAll();
            ObservableList<Event> observableList = FXCollections.observableList(events);
            EventTable.setItems(observableList);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            codeCol.setCellValueFactory(new PropertyValueFactory<>("MeetingCode"));
            dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startTCol.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
            EndTCol.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
            coachCol.setCellValueFactory(new PropertyValueFactory<>("coach"));
            int coachId = Integer.parseInt(coachCol.getCellFactory().toString());
            Coach c = cs.getOne(coachId);
            coachCol.setText(c.getFirstname()+" "+c.getLastname());

        }catch (Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

}
