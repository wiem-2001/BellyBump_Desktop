package tn.esprit.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import tn.esprit.MainFX;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class usersDhashboard {

    @FXML
    private TableColumn<User, String> actionC;
    @FXML
    private TableColumn<User, String> birthdayC;
    @FXML
    private TableColumn<User, String> emailC;
    @FXML
    private TableColumn<User, String> nameC;
    @FXML
    private TableColumn<User, String> statusC;
    @FXML
    private TableView<User> usersTableView;
    private UserServices userServices=new UserServices();
    @FXML
    private Text userEmailT;


    @FXML
    public void initialize() {
        User user=userServices.getOne(MainFX.getLoggedInUserEmail());
        //userEmailT.setText(user.getEmail());
        List<User> users = userServices.getAll();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        birthdayC.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        emailC.setCellValueFactory(new PropertyValueFactory<>("email"));
        statusC.setCellValueFactory(cellData -> {
            String status = cellData.getValue().getStatus() == 0 ? "Desactived" : "Activated";
            return new SimpleStringProperty(status);
        });
        nameC.setCellValueFactory(cellData -> {
            String last_name = cellData.getValue().getFirst_name();
            String first_name = cellData.getValue().getLast_name();
            return new SimpleStringProperty(first_name + " " + last_name);
        });


        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, String> call(final TableColumn<User, String> param) {
                final TableCell<User, String> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button activateButton = new Button("Activate/Deactivate");
                    {
                        deleteButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete User");
                            alert.setContentText("Are you sure you want to delete this user?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                userServices.delete(user);
                                usersTableView.getItems().clear();
                                usersTableView.getItems().addAll(userServices.getAll());
                            }
                        });
                        activateButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (user.getStatus() == 0) {
                                userServices.updateStatus(user);
                                activateButton.setText("Deactivate");
                             //   activateButton.getStyleClass().add("button-deactivate");
                                reloadTableView();
                            } else {
                                userServices.updateStatus(user);
                                activateButton.setText("Activate");
                            //    activateButton.getStyleClass().remove("button-deactivate");
                                reloadTableView();
                            }
                        });
                    }
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            User user = getTableView().getItems().get(getIndex());
                            HBox buttons = new HBox(deleteButton);
                            if (user.getStatus() == 0) {
                                activateButton.setText("Activate");
                                activateButton.setOnAction(event -> {
                                    userServices.updateStatus(user);
                                    reloadTableView();
                                });
                                buttons.getChildren().add(activateButton);
                            } else {
                                // User is activated, show deactivate button
                                activateButton.setText("Deactivate");
                                activateButton.setOnAction(event -> {
                                    userServices.updateStatus(user);
                                    reloadTableView();
                                });
                                buttons.getChildren().add(activateButton);
                            }
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        actionC.setCellFactory(cellFactory);
        usersTableView.setItems(observableUsers);
        usersTableView.setItems(observableUsers);
      //  usersTableView.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }
    private void reloadTableView() {
        List<User> users = userServices.getAll();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        usersTableView.getItems().clear();
        usersTableView.getItems().addAll(observableUsers);
    }
    @FXML
    public void logoutLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToLogin(node);

    }
}
