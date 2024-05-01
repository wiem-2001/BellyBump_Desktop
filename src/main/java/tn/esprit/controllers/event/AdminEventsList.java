package tn.esprit.controllers.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.esprit.controllers.adminSideBarController;
import tn.esprit.controllers.coach.AddCoach;
import tn.esprit.controllers.coach.CoachController;
import tn.esprit.controllers.motherSideBarController;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;

import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminEventsList   {

    @FXML
    private TextField searchFld;

    @FXML
    private TableColumn<Event, String> EndTCol;

    @FXML
    private TableView<Event> EventTable;

    @FXML
    private TableColumn<Event, String> actionBtnsCol;

    @FXML
    private Button addEventBtn;

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
    private Button refreshTable;

    @FXML
    private TableColumn<Event, String> startTCol;

    final EventService es = new EventService();
    final CoachService cs = new CoachService();

    ObservableList<Event> observableList = FXCollections.observableArrayList();
    Event event=null;


    @FXML
    void getAddEvent() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/event/addEvent.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    void refreshTable() {
        observableList.clear();
        List<Event> events= es.getAll();
        for (Event event:events) {
            observableList.add(event);
            EventTable.setItems(observableList);
        }
        searchFld.setText(null);
    }

    @FXML
    private VBox sidebar;

    @FXML
    void initialize(){
        loadData();
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/adminSideBar.fxml"));
        try{
            VBox sideBar = fxmlLoader1.load();
            adminSideBarController eventController=fxmlLoader1.getController();
            sidebar.getChildren().add(sideBar);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        searchFld.setOnKeyReleased((e)->{
            String query = searchFld.getText();
            if (!query.isEmpty()) {
                List<Event> filteredEvents = es.search(query);
                observableList.clear();
                observableList.addAll(filteredEvents);
                EventTable.setItems(observableList);
            } else {
                // If the query is empty, display all events
                refreshTable();
            }
        });
    }

    private void loadData() {
        refreshTable();
        try {
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            codeCol.setCellValueFactory(new PropertyValueFactory<>("MeetingCode"));
            dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            startTCol.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
            EndTCol.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
            coachCol.setCellValueFactory(new PropertyValueFactory<>("coach"));

           /* Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFactoryCoach = (TableColumn<Event, String> param) -> {
                // Make cell containing buttons
                final TableCell<Event, String> cell = new TableCell<Event, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        // Cell is only created for non-empty rows
                        if (empty) {
                            setText(null);
                        } else {
                            try {
                                int coachId = Integer.parseInt(item);
                                System.out.println("coachId"+coachId);
                                System.out.println("item"+item);
                                Coach c = cs.getOne(coachId);
                                String coachName = (c != null) ? (c.getFirstname() + " " + c.getLastname()) : "___";
                                setText(coachName);
                            } catch (NumberFormatException e) {
                                setText(null);
                            }
                        }
                    }
                };

                return cell;
            };

            coachCol.setCellFactory(cellFactoryCoach);*/

            // Action Column
            Callback<TableColumn<Event, String>, TableCell<Event, String>> cellFoctory = (TableColumn<Event, String> param) -> {
                // make cell containing buttons
                final TableCell<Event, String> cell = new TableCell<Event, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            ImageView deleteButton = new ImageView();
                            ImageView editButton = new ImageView();
                            String uploadFolder = "C:/Users/Eya/Downloads/BellyBump_Desktop/src/main/resources/iconImages/";
                            String deleteimgName="delete-icon.png";
                            String editimgName="edit-icon.png";

                            File destFile = new File(uploadFolder, deleteimgName);
                            Image deleteimage = new Image(destFile.toURI().toString());
                            deleteButton.setImage(deleteimage);
                            deleteButton.setFitHeight(40);
                            deleteButton.setFitWidth(32);
                            deleteButton.setStyle("-fx-cursor: hand;");

                            File destFile2 = new File(uploadFolder, editimgName);
                            Image editimage = new Image(destFile2.toURI().toString());
                            editButton.setImage(editimage);
                            editButton.setFitHeight(30);
                            editButton.setFitWidth(35);
                            editButton.setStyle("-fx-cursor: hand;");



                            //deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/iconImages/delete-icon.png"))));
                            // editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/iconImages/edit-icon.png"))));

                            deleteButton.setOnMouseClicked(actionEvent -> {

                                try {
                                    event = EventTable.getItems().get(getIndex());
                                    es.delete(event);
                                    refreshTable();

                                } catch (Exception ex) {
                                    Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
                                }





                            });
                            editButton.setOnMouseClicked(( actionEvent) -> {

                                event = EventTable.getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("/event/AddEvent.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                AddEvent addEventController = loader.getController();
                                addEventController.setUpdate(true);
                                addEventController.setEventField(event.getId(), event.getName(), event.getDescription(), event.getMeetingCode(), (Date) event.getDay(),
                                        event.getHeureDebut(),event.getHeureFin(),event.getCoach());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });

                            HBox buttonsContainer = new HBox(editButton, deleteButton);
                            buttonsContainer.setAlignment(Pos.CENTER);
                            setGraphic(buttonsContainer);
                            setText(null);

                        }
                    }

                };

                return cell;
            };
            actionBtnsCol.setCellFactory(cellFoctory);

            EventTable.setItems(observableList);
            EventTable.getStylesheets().add(getClass().getResource("/css/TableStyle.css").toExternalForm());
            EventTable.setStyle(
                    "-fx-background-color: #df548a;" + // Set table background color
                             "-fx-border-color: #df548a;" + // Set border color
                            "-fx-border-width: 1px;" // Set border width
            );

            EventTable.applyCss();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    /*
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
    */
}