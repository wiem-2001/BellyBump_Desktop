package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.Task;
import tn.esprit.entities.User;
import tn.esprit.services.TasksServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class taskController {
    @FXML
    private TextArea taskDescTF;

    @FXML
    private Text tastDateT,dateModifLabel;
    @FXML
    private TextField taskTitleT;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView saveIcon;
    @FXML
    private ComboBox<String> tagsCombox;
    @FXML
    ImageView profileImageView;
    @FXML
    ImageView backToTasks;

    UserServices us=new UserServices();
    userProfilController userC=new userProfilController();
    private Task task;
    private TasksServices taskService=new TasksServices();
    private UserServices userServices=new UserServices();

    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            initialize();
        }
    }

    public void initialize() {
        User user = us.getOne(MainFX.getLoggedInUserEmail());

        if (task != null) {
            taskTitleT.setText(task.getTitle());
            taskDescTF.setText(task.getDescription());
            if (task.getDateLastModification() != null) {
                tastDateT.setText(task.getDateLastModification().toString());
            } else {
                tastDateT.setText("No modification date");
            }
            dateModifLabel.setText("Derniére date de modification : ");
            if (task.getTag() != null) {
                tagsCombox.setValue(task.getTag());
            }
        } else {
            tagsCombox.setPromptText("Select a tag...");
            tagsCombox.getItems().addAll(
                    "Other",
                    "Baby",
                    "Mother",
                    "Shop",
                    "Events"
            );
            saveIcon.setImage(new Image("/assets/images/saveButotn.png"));
            deleteIcon.setImage(new Image("/assets/images/deleteIcon.png"));
            tastDateT.setText("");
            dateModifLabel.setText("");
        }

        backToTasks.setOnMouseClicked(event1 ->{
            FXMLLoader loader2= new FXMLLoader();
            loader2.setLocation(getClass().getResource("/motherSideBar.fxml"));
            Parent root = null;
            try {
                root = loader2.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            motherSideBarController controller = loader2.getController();
            controller.setTasksList();
            Stage stage= (Stage) backToTasks.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

    @FXML
    public void saveTaskOnMouseClicked() {
        User user = new User();
        user = userServices.getOne(MainFX.getLoggedInUserEmail());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
        try {
            Parent root = loader.load();
            confirmationDialogController controller = loader.getController();
            Task task = new Task();
            task.setTitle(taskTitleT.getText());
            task.setDescription(taskDescTF.getText());

            // Get the selected tag from the ComboBox
            String selectedTag = tagsCombox.getValue();
            if (selectedTag == null || selectedTag.isEmpty()) {
                // If no tag is selected, set the tag value to "Not Selected"
                task.setTag("Not Selected");
            } else {
                // If a tag is selected, set the tag value to the selected tag
                task.setTag(selectedTag);
            }
            controller.setTask(task);
            Stage confirmationStage = new Stage();
            confirmationStage.initModality(Modality.APPLICATION_MODAL);
            confirmationStage.setScene(new Scene(root));
            confirmationStage.showAndWait();
            if (controller.isConfirmed()) {
                if (task != null) {
                    System.out.println(task.toString());
                    if (taskService.getOneById(task.getId(), user.getId()) != null) {
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
            controller.setTask(task);
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
                            FXMLLoader loader2= new FXMLLoader();
                            loader2.setLocation(getClass().getResource("/motherSideBar.fxml"));
                            Parent parentRoot = null;
                            try {
                                parentRoot = loader2.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            motherSideBarController  motherSideBarController = loader2.getController();
                        motherSideBarController.setTasksList();
                            Stage stage= (Stage) backToTasks.getScene().getWindow();
                            Scene scene = new Scene(parentRoot);
                            stage.setScene(scene);
                            stage.show();
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