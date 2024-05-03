package tn.esprit.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationManager {
    public static void loadView(String resource, String title, Node node) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(resource));
            Parent root = loader.load();
            Scene currentScene = getCurrentScene(node);
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Scene getCurrentScene(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        return stage.getScene();
    }

    public static void navigateToTasksView(Node node) {
        loadView("/tasksUI.fxml", "Tasks", node);
    }

    public static void navigateToUpdatePassword(Node node) {
        loadView("/updatePasswordUi.fxml", "Update Profil", node);
    }

    public static void navigateToUserProfil(Node node) {
        loadView("/userProfilUI.fxml", "User Profil", node);
    }

    public static void navigateToLogin(Node node) {
        loadView("/loginUI.fxml", "login", node);
    }
}