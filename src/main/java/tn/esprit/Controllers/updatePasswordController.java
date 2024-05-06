package tn.esprit.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.MainFX;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;

public class updatePasswordController {
    UserServices us=new UserServices();
    userProfilController userC=new userProfilController();
    @FXML
    PasswordField currentPasswordTF,newPasswordTF,confirmPasswordTF;

    @FXML
    Text currentPasswordET,passwordET,confirmPasswordET,errorT;
    @FXML
    ImageView profileImageView;

    public void initialize() {
        String userEmail = MainFX.getLoggedInUserEmail();

        if (userEmail != null && !userEmail.isEmpty()) {
            User user = us.getOne(userEmail);
            if (user != null) {
                String imageName = user.getImage();
                if (imageName != null && !imageName.isEmpty()) {
                    String imagePath = userC.getUserImageDirectory() + imageName;
                    try {
                        File file = new File(imagePath);
                        URL url = file.toURI().toURL();
                        Image image = new Image(url.toString());
                      //todo to set
                        // profileImageView.setImage(image);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("User not found");
            }
        } else {
            System.out.println("User email is null or empty");
        }
    }

    @FXML
    public void updatePasswordButtonOnAction() {
        String currentPassword = currentPasswordTF.getText();
        String newPassword = newPasswordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        String email = MainFX.getLoggedInUserEmail();
        boolean passwordMatch = newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorT.setText("Please fill all the fields to update your password");
            passwordET.setText("");
            confirmPasswordET.setText("");
        } else {
            String storedPassword = us.getPasswordByEmail(email);
            boolean result = us.verifyPassword(currentPassword, storedPassword);
            if (!result) {
                errorT.setText("Current password is incorrect.");
            } else if (currentPassword.equals(newPassword)) {
                errorT.setText("New password should be different from the current password.");
                passwordET.setText("");
                confirmPasswordET.setText("");
            } else if (!passwordMatch) {
                errorT.setText("");
                passwordET.setText("Password must contain at least 6 characters \n (uppercase letters, lowercase letters, and special characters.)");
            }
            else if (!newPassword.equals(confirmPassword)) {
                errorT.setText("");
                confirmPasswordET.setText("Passwords do not match.");
            }  else {
                errorT.setText("");
                passwordET.setText("");
                confirmPasswordET.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
                try {
                    Parent root = loader.load();
                    confirmationDialogController controller = loader.getController();
                    Stage confirmationStage = new Stage();
                    confirmationStage.initModality(Modality.APPLICATION_MODAL);
                    confirmationStage.setScene(new Scene(root));
                    confirmationStage.showAndWait();
                    if (controller.isConfirmed()) {
                        confirmPasswordET.setText("");
                        passwordET.setText("");
                        us.updatePassword(email, newPassword);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void navigateToTasksOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToTasksView(node);
    }


}
