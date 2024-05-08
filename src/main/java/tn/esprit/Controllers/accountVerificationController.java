package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.EmailContentBuilder;
import tn.esprit.util.EmailSender;
import tn.esprit.util.ResetToken;


import java.io.IOException;

import static tn.esprit.Controllers.resetPasswordRequestController.generateResetToken;

public class accountVerificationController {
    private String email;
    @FXML
    private TextField codeTF;
    @FXML
    private Text errorTF;
    @FXML
    ImageView backToTasks;
    UserServices us=new UserServices();
    public void initialize()
    {
        backToTasks.setOnMouseClicked(event1 ->{
            FXMLLoader loader2= new FXMLLoader();
            loader2.setLocation(getClass().getResource("/LoginUI.fxml"));
            Parent root = null;
            try {
                root = loader2.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage= (Stage) backToTasks.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
    @FXML
    public void verifyAccountButtonOnAction(ActionEvent event) {
        String enteredCode = codeTF.getText();
        String verificationCode=us.getOne(email).getVerificationCode();
        if (enteredCode.equals(verificationCode)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/motherSideBar.fxml"));
                Parent nextUI = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene currentScene = new Scene(nextUI);
                stage.setScene(currentScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorTF.setText("Invalid verification code. Please try again.");
        }
    }

    public void resentCodeLink(ActionEvent event) {
        User user=us.getOne(this.email);
        ResetToken resetToken = generateResetToken();
        String verificationCode = resetToken.getToken();
        us.updateVerificationCode(email, verificationCode);
        EmailSender.sendEmail(email, "Your Account Verification Code ", EmailContentBuilder.buildVerificationCodeEmailContent(user, verificationCode));
    }
    public void setUserEmail(String email) {
        System.out.println(email);
        this.email =email;
    }
}
