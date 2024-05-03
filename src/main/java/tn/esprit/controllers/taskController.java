package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    UserServices us=new UserServices();
    userProfilController userC=new userProfilController();
    private Task task;
    private TasksServices taskService=new TasksServices();
    private UserServices userServices=new UserServices();
    @FXML
    Pane sidebar;
    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            initialize();
        }
    }

    public void initialize() {
        User user = us.getOne(MainFX.getLoggedInUserEmail());
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(getClass().getResource("/motherSideBar.fxml"));
        try{
            VBox sideBar = fxmlLoader1.load();
            sidebar.getChildren().add(sideBar);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
       /* String imageName = user.getImage(); // Assuming it contains only the image name
        String imagePath = userC.getUserImageDirectory() + imageName; // Concatenate directory and image name
        try {
            File file = new File(imagePath);
            URL url = file.toURI().toURL();
            Image image = new Image(url.toString());
            profileImageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        tagsCombox.setPromptText("Select a tag...");
        tagsCombox.getItems().addAll(
                "Other",
                "Baby",
                "Mother",
                "Shop",
                "Events"
        );*/
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
            saveIcon.setImage(new Image("/assets/images/saveButotn.png"));
            deleteIcon.setImage(new Image("/assets/images/deleteIcon.png"));
            tastDateT.setText("");
            dateModifLabel.setText("");
        }
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
