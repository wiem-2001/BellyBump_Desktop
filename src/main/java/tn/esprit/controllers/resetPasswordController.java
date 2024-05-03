package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.time.LocalDateTime;

public class resetPasswordController {
    @FXML
    private TextField resetPassCode;
    @FXML
    private Text errorText;
    private String email;
    private UserServices us=new UserServices();
    @FXML
    public void handleVerifyCode(ActionEvent event) {
        String enteredCode = resetPassCode.getText();
        String resetToken=us.getOne(email).getReset_token();
        if (enteredCode.equals(resetToken)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatePasswordAfterReset.fxml"));
                Parent nextUI = loader.load();
                updatePasswordAfterResetController controller = loader.getController();
                controller.setUserEmail(email); // Pass email as data to the update password controller
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene currentScene = new Scene(nextUI);
                stage.setScene(currentScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorText.setText("Invalid reset code. Please try again.");
        }
    }

    // Setter method for expiryDateTime
    public void setUserEmail(String email) {
        System.out.println(email);
        this.email =email;
    }
}
