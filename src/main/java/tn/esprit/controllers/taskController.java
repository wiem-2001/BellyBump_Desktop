package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.Task;
import tn.esprit.entities.User;
import tn.esprit.services.TasksServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

public class taskController {
    @FXML
    private TextArea taskDescTF;

    @FXML
    private Text tastDateT,dateModifLabel;
    @FXML
    private TextField taskTitleT;
    @FXML
    private Text userEmailT;
    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView saveIcon;
    private Task task;
    private TasksServices taskService=new TasksServices();
    private UserServices userServices=new UserServices();
    public void initialize(Task task) {

    }
    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            initialize();
        }
    }

    public void initialize() {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
        if (task != null) {
            taskTitleT.setText(task.getTitle());
            taskDescTF.setText(task.getDescription());
            tastDateT.setText(task.getDateLastModification().toString());
            dateModifLabel.setText("Derniére date de modification : ");
        }else{
            saveIcon.setImage(new Image("/assets/images/saveButotn.png"));
            deleteIcon.setImage(new Image("/assets/images/deleteIcon.png"));
            tastDateT.setText("");
            dateModifLabel.setText("");
        }
    }
    @FXML
    public void saveTaskOnMouseClicked() {
        User user = new User();
        user = userServices.getOne(userEmailT.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
        try {
            Parent root = loader.load();
            confirmationDialogController controller = loader.getController();
            Task task = new Task();
            task.setTitle(taskTitleT.getText());
            task.setDescription(taskDescTF.getText());
            controller.setTask(task);
            Stage confirmationStage = new Stage();
            confirmationStage.initModality(Modality.APPLICATION_MODAL);
            confirmationStage.setScene(new Scene(root));
            confirmationStage.showAndWait();
            if (controller.isConfirmed()) {
                if (task != null) {
                    System.out.println(task.toString());
                    if (taskService.getOne(task.getId(), user.getId()) != null) {
                        taskService.update(task);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Task updated successfully!");
                        successAlert.showAndWait();
                    } else {
                        taskService.add(task, user.getId());
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Task added successfully!");
                        successAlert.showAndWait();
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No task selected!");
                    errorAlert.showAndWait();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void deleteTaskonMouseClicked() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
        try {
            Parent root = loader.load();
            confirmationDialogController controller = loader.getController();
            controller.setTask(task); // Assuming you set the task in the controller
            Stage confirmationStage = new Stage();
            confirmationStage.initModality(Modality.APPLICATION_MODAL);
            confirmationStage.setScene(new Scene(root));
            confirmationStage.showAndWait();
            if (controller.isConfirmed()) {
                if (task != null) {
                    taskService.delete(task);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Task deleted successfully!");
                    successAlert.showAndWait().ifPresent(response -> {
                        try {
                            FXMLLoader tasksLoader = new FXMLLoader(getClass().getResource("/tasksUI.fxml"));
                            Parent tasksRoot = tasksLoader.load();
                            Stage stage = (Stage) taskTitleT.getScene().getWindow();
                            stage.setScene(new Scene(tasksRoot));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No task selected!");
                    errorAlert.showAndWait();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void userProfilLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToUserProfil(node);
    }
    @FXML
    public void navigateToTasksOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToTasksView(node);
    }
    public void onDeleteIconMouseEntered() {
        deleteIcon.setImage(new Image("/assets/images/deleteIconOnHover.png"));
    }
    public void onDeleteIconMouseExited() {
        deleteIcon.setImage(new Image("/assets/images/deleteIcon.png"));
    }
    public void onSaveIconMouseEntered() {
        saveIcon.setImage(new Image("/assets/images/saveIconOnHover.png"));
    }
    public void onSaveIconMouseExited() {
        saveIcon.setImage(new Image("/assets/images/saveButotn.png"));
    }
}
