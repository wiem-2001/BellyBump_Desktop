package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.EmailContentBuilder;
import tn.esprit.util.EmailSender;
import tn.esprit.util.NavigationManager;
import tn.esprit.util.ResetToken;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static tn.esprit.services.UserServices.isValidEmail;

public class resetPasswordRequestController {
    UserServices us = new UserServices();
    @FXML
    private TextField userEmailTF;
    @FXML
    private Text errorTF;
    private String token;
    private LocalDateTime expiryDateTime;
    public static ResetToken generateResetToken() {
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 6); // Get the first 6 characters of the UUID string
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(5); // Set expiry time to 5 minutes from now
        return new ResetToken(token, expiryDateTime);
    }
    @FXML
        public void handleResetPasswordRequest(ActionEvent event) {
            String email = userEmailTF.getText();
            if (email.isEmpty()) {
                errorTF.setText("Please enter your email to reset your password");
            } else if (!isValidEmail(email)) {
                errorTF.setText("Please enter a valid email address.");
            } else {
                errorTF.setText("");
                User user = us.getOne(email);
                if (user != null) {
                    ResetToken resetToken = generateResetToken();
                    String token = resetToken.getToken();
                    LocalDateTime expiryDateTime = resetToken.getExpiryDateTime();
                    us.saveResetToken(email, token); // Save token and expiry time in the database
                    EmailSender.sendEmail(email, "Reset Password ", EmailContentBuilder.buildResetPasswordEmailContent(user, token));
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetPasswordUI.fxml"));
                        Parent nextUI = loader.load();
                        resetPasswordController resetPasswordController = loader.getController();
                        // Pass the reset token and expiry date time to the controller
                        resetPasswordController.setUserEmail(email); // Assuming you have setter methods in your controller
                       // resetPasswordController.setExpiryDateTime(expiryDateTime);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene currentScene = new Scene(nextUI);
                        stage.setScene(currentScene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorTF.setText("User with such email not found");
                }
            }
        }
        @FXML
    public void registerLinkOnClik(ActionEvent event){
            Node node=(Node) event.getSource() ;
            NavigationManager.loadView("/registerUI.fxml","register UI",node);
        }

    }
