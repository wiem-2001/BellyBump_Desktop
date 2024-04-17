package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.MainFX;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class updatePasswordController {
    UserServices us=new UserServices();
    @FXML
    PasswordField currentPasswordTF,newPasswordTF,confirmPasswordTF;

    @FXML
    Text currentPasswordET,passwordET,confirmPasswordET,errorT,userEmailT;
    public void initialize() {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
    }
    @FXML
    public void updatePasswordButtonOnAction() {
        String currentPassword = currentPasswordTF.getText();
        String newPassword = newPasswordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        String email = userEmailT.getText();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorT.setText("Please fill all the fields to update your password");
        } else {
            String storedPassword = us.getPasswordByEmail(email);
            boolean result = us.verifyPassword(currentPassword, storedPassword);
            if (!result) {
                errorT.setText("Current password is incorrect.");
            } else if (currentPassword.equals(newPassword)) {
                errorT.setText("New password should be different from the current password.");
            } else {
                errorT.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
                try {
                    Parent root = loader.load();
                    confirmationDialogController controller = loader.getController();
                    Stage confirmationStage = new Stage();
                    confirmationStage.initModality(Modality.APPLICATION_MODAL);
                    confirmationStage.setScene(new Scene(root));
                    confirmationStage.showAndWait();

                    if (controller.isConfirmed()) {
                        if (!newPassword.equals(confirmPassword)) {
                            confirmPasswordET.setText("Passwords do not match.");
                        } else if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
                            passwordET.setText("Password must contain at least 6 characters \n (uppercase letters, lowercase letters, and special characters.)");
                        } else {
                            confirmPasswordET.setText("");
                            passwordET.setText("");
                            // Update password
                            us.updatePassword(email, newPassword);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void logoutLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.logout(node);
    }
    @FXML
    public void updatePasswordLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToUpdatePassword(node);
    }
    @FXML
    public void userProfileLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToUserProfil(node);
    }

    @FXML
    public void navigateToTasksOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToTasksView(node);
    }


}
