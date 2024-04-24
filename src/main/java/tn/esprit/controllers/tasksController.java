package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class tasksController {
    @FXML
    private ListView<String> taskListView;
    @FXML
    private Text userEmailT;
    @FXML
    private ImageView addTasIcon;
    @FXML
    private ComboBox<String> sortingCombox;
    @FXML
    ImageView profileImageView;
    private TasksServices taskService = new TasksServices();
    UserServices us=new UserServices();
    userProfilController userC=new userProfilController();
    @FXML
    public void initialize() {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
        User user = us.getOne(MainFX.getLoggedInUserEmail());
        String imageName = user.getImage(); // Assuming it contains only the image name
        String imagePath = userC.getUserImageDirectory() + imageName; // Concatenate directory and image name
        try {
            File file = new File(imagePath);
            URL url = file.toURI().toURL();
            Image image = new Image(url.toString());
            profileImageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        sortingCombox.setPromptText("Sort by...");
        sortingCombox.getItems().addAll(
                "Other",
                "Baby",
                "Mother",
                "Shop",
                "Events"
        );
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
    private void handleSortingComboBox(ActionEvent event) {
        String selectedTag = sortingCombox.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            List<Task> tasks = taskService.getAllByTag(MainFX.getLoggedInUserEmail(), selectedTag);
            ObservableList<String> taskTitles = FXCollections.observableArrayList();
            for (Task task : tasks) {
                taskTitles.add(task.getTitle());
            }
            taskListView.setItems(taskTitles);
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
        NavigationManager.navigateToLogin(node);
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
@FXML
    public void addTaskOnClick(javafx.scene.input.MouseEvent mouseEvent) {
        Node node=(Node) mouseEvent.getSource() ;
        NavigationManager.loadView("/taskUI.fxml","Add task UI",node);
    }
}

