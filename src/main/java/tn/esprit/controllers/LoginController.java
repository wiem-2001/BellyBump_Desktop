package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.User;
import tn.esprit.enums.UserRole;
import tn.esprit.services.UserServices;
import java.io.IOException;
import static tn.esprit.services.UserServices.isValidEmail;
public class LoginController {
    UserServices us = new UserServices();
    @FXML
    private TextField userEmailTF, userPassordTF;
    @FXML
     private  Text errorTF;

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        String email = userEmailTF.getText();
        String password = userPassordTF.getText();
        String loginSuccessful = us.login(email, password);
        if (email.isEmpty() || password.isEmpty()) {
            errorTF.setText("Please fill all the fields.");
        } else {
            if (loginSuccessful.equals("true")) {
                MainFX.setLoggedInUserEmail(email);
                User user = us.getOne(email);
                try {
                    FXMLLoader loader;
                    Parent registerUI;
                    if (user.getRole() == UserRole.ROLE_MOTHER) {
                        loader = new FXMLLoader(getClass().getResource("/tasksUI.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("/usersDashboard.fxml"));
                    }
                    registerUI = loader.load();
                    Scene currentScene = ((Node) event.getSource()).getScene();
                    Stage stage = (Stage) currentScene.getWindow();
                    Scene registerScene = new Scene(registerUI);
                    stage.setScene(registerScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (loginSuccessful.equals("incorrectCredentiels")) {
                errorTF.setText("Invalid email or password. Please try again.");
            } else if (!isValidEmail(email)) {
                errorTF.setText("Please enter a valid email address.");
            } else if (loginSuccessful.equals("userNotFound")) {
                errorTF.setText("There is no user with the provided email.");
            } else {
                errorTF.setText("An unexpected error occurred. Please try again later.");
            }
        }
    }

    @FXML
    public void registerLinkOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registerUI.fxml"));
            Parent registerUI = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene registerScene = new Scene(registerUI);
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


