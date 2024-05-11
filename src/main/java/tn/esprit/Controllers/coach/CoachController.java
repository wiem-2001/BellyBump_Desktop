package tn.esprit.Controllers.coach;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.esprit.Controllers.adminSideBarController;
import tn.esprit.Controllers.motherSideBarController;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.services.CoachService;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoachController  implements Initializable {
    @FXML
    private TextField searchFld;

    @FXML
    private TableColumn<Coach, String> actionsCol;

    @FXML
    private TableColumn<Coach, String> emailCol;

    @FXML
    private TableColumn<Coach, String> firstnameCol;

    @FXML
    private TableColumn<Coach, Integer> phoneCol;

    @FXML
    private TableColumn<Coach, String> jobCol;

    @FXML
    private TableColumn<Coach, String> lastnameCol;

    @FXML
    private TableView<Coach> coachTableView;



    CoachService cs = new CoachService();

    ObservableList<Coach> observableList = FXCollections.observableArrayList();
    Coach coach=null;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        loadDate();


        searchFld.setOnKeyReleased((e)->{
            String query = searchFld.getText();
            if (!query.isEmpty()) {
                List<Coach> filteredCoaches = cs.search(query);
                observableList.clear();
                observableList.addAll(filteredCoaches);
                coachTableView.setItems(observableList);
            } else {
                // If the query is empty, display all events
                refreshTable();
            }
        });
    }

    private void loadDate(){
        refreshTable();

        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        jobCol.setCellValueFactory(new PropertyValueFactory<>("job"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));



        //add cell of button edit
        Callback<TableColumn<Coach, String>, TableCell<Coach, String>> cellFoctory = (TableColumn<Coach, String> param) -> {
            // make cell containing buttons
            final TableCell<Coach, String> cell = new TableCell<Coach, String>() {
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

                        String projectDir = System.getProperty("user.dir");
                        String uploadFolder = projectDir+"/src/main/resources/iconImages";//"C:/Users/user/IdeaProjects/bellyBump_Desktop/src/main/resources/iconImages/";//
                        String deleteimgName="delete-icon.png";
                        String editimgName="edit-icon.png";

                        File destFile = new File(uploadFolder, deleteimgName);
                        Image deleteimage = new Image(destFile.toURI().toString());
                        deleteButton.setImage(deleteimage);
                        deleteButton.setFitHeight(32);
                        deleteButton.setFitWidth(30);
                        deleteButton.setStyle("-fx-cursor: hand;");

                        File destFile2 = new File(uploadFolder, editimgName);
                        Image editimage = new Image(destFile2.toURI().toString());
                        editButton.setImage(editimage);
                        editButton.setFitHeight(28);
                        editButton.setFitWidth(31);
                        editButton.setStyle("-fx-cursor: hand;");


                        //deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/iconImages/delete-icon.png"))));
                       // editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/iconImages/edit-icon.png"))));

                        deleteButton.setOnMouseClicked(event -> {
                            coach = coachTableView.getItems().get(getIndex());
                            FXMLLoader loader1 = new FXMLLoader ();
                            loader1.setLocation(getClass().getResource("/coach/deleteCoachConfirmation.fxml"));
                            try {
                                loader1.load();
                            } catch (IOException ex) {
                                Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            deleteCoach deleteCoach= loader1.getController();
                            deleteCoach.setCoach(coach);
                            Parent parent = loader1.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });
                        editButton.setOnMouseClicked(( event) -> {

                            coach = coachTableView.getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/coach/AddCoach.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddCoach addCoachController = loader.getController();
                            addCoachController.setUpdate(true);
                            addCoachController.setTextField(coach.getId(), coach.getFirstname(),
                                    coach.getLastname(),coach.getJob(),coach.getPhone(), coach.getEmail());
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
        actionsCol.setCellFactory(cellFoctory);
        coachTableView.setItems(observableList);
        coachTableView.getStylesheets().add(getClass().getResource("/css/TableStyle.css").toExternalForm());
        coachTableView.setStyle(
                "-fx-background-color: #df548a;" + // Set table background color
                        "-fx-border-color: #df548a;" + // Set border color
                        "-fx-border-width: 1px;" // Set border width
        );

        coachTableView.applyCss();

    }




    @FXML
    void getAddCoach() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/coach/addCoach.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    void refreshTable() {
        observableList.clear();
        List<Coach> coaches= cs.getAll();
        for (Coach coach:coaches) {
            observableList.add(coach);
            coachTableView.setItems(observableList);
        }
        searchFld.setText(null);
    }
}
