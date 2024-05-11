package tn.esprit.Controllers;
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
import tn.esprit.util.EmailContentBuilder;
import tn.esprit.util.EmailSender;
import tn.esprit.util.NavigationManager;
import tn.esprit.util.ResetToken;

import java.io.IOException;
import java.time.LocalDateTime;

import static tn.esprit.Controllers.resetPasswordRequestController.generateResetToken;
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
                System.out.println(user.toString());
                        if (user.getRole().getRoleName().equals(UserRole.ROLE_MOTHER.getRoleName()) && user.getStatus()==0) {
                            errorTF.setText("This account has been desactivated by the administrator");
                        }
                    if (user.getRole().getRoleName().equals(UserRole.ROLE_MOTHER.getRoleName()) && user.getStatus()==1){
                        ResetToken resetToken = generateResetToken();
                        String verificationCode = resetToken.getToken();
                        us.updateVerificationCode(email, verificationCode);
                        EmailSender.sendEmail(email, "Your Account Verification Code ", EmailContentBuilder.buildVerificationCodeEmailContent(user, verificationCode));
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/verificationAccount.fxml"));
                            Parent nextUI = loader.load();
                            accountVerificationController accountVerificationController = loader.getController();
                            // Pass the reset token and expiry date time to the controller
                            accountVerificationController.setUserEmail(email); // Assuming you have setter methods in your controller
                            // resetPasswordController.setExpiryDateTime(expiryDateTime);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene currentScene = new Scene(nextUI);
                            stage.setScene(currentScene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if(user.getRole().getRoleName().equals(UserRole.ROLE_ADMIN.getRoleName()) ){
                        Node node=(Node) event.getSource() ;
                        NavigationManager.loadView("/adminSideBar.fxml","Dashboard ",node);
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
        Node node=(Node) event.getSource() ;
        NavigationManager.loadView("/registerUI.fxml ","register UI ",node);
    }
    @FXML
    public void handleforgetPassword(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.loadView("/resetPasswordRequestUI.fxml ","reset password request  UI ",node);
    }
}


