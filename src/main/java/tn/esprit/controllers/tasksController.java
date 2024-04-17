package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.Task;
import tn.esprit.entities.User;
import tn.esprit.services.TasksServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.IOException;
import java.util.List;

public class tasksController {
    @FXML
    private ListView<String> taskListView;
    @FXML
    private Text userEmailT;
    @FXML
    private ImageView addTasIcon;
    private TasksServices taskService = new TasksServices();
    @FXML
    public void initialize() {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
        List<Task> tasks = taskService.getAll(MainFX.getLoggedInUserEmail());
        ObservableList<String> taskTitles = FXCollections.observableArrayList();
        for (Task task : tasks) {
            taskTitles.add(task.getTitle());
        }
        taskListView.setItems(taskTitles);
        taskListView.setOnMouseClicked(event -> {
            // Get the selected task title
            String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTaskTitle != null) {
                Task selectedTask = null;
                for (Task task : tasks) {
                    if (task.getTitle().equals(selectedTaskTitle)) {
                        selectedTask = task;
                        break;
                    }
                }
                if (selectedTask != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/taskUI.fxml"));
                        Parent root = loader.load();
                        taskController taskController = loader.getController();
                        taskController.setTask(selectedTask);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @FXML
    private void addTaskOnClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/taskUI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addTaskIconMouseEntered() {
        addTasIcon.setImage(new Image("/assets/images/addTaskIconOnClick.png"));
    }
    public void addTaskIconMouseExited() {
        addTasIcon.setImage(new Image("/assets/images/addTaskIcon.png"));
    }
    @FXML
    public void logoutLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.logout(node);
        MainFX.setLoggedInUserEmail("");
    }
    @FXML
    public void updatePasswordLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
      NavigationManager.navigateToUpdatePassword(node);
    }
    @FXML
    public void navigateToTasksOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
       NavigationManager.navigateToTasksView(node);
        }
    @FXML
    public void userProfileLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToUserProfil(node);
    }
    }


