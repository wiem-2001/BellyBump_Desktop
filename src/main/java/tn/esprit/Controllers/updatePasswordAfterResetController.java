package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;

import java.io.IOException;

public class updatePasswordAfterResetController {
    @FXML
   private PasswordField newPasswordTF,confirmPasswordTF;
   @FXML
   private Text errorTF;
  private UserServices us=new UserServices();
  private String email;
    @FXML
    public void updatePasswordButtonOnAction() {
        String newPassword = newPasswordTF.getText();
        String confirmPassword = confirmPasswordTF.getText();
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorTF.setText("Please fill all the fields to set your new password");
        } else {
            errorTF.setText("");
            try {
                    if (!newPassword.equals(confirmPassword)) {
                        errorTF.setText("Passwords do not match.");
                    } else if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
                        errorTF.setText("Password must contain at least 6 characters \n (uppercase letters, lowercase letters, and special characters.)");
                    } else {
                        errorTF.setText("");
                        us.updatePassword(email, newPassword);
                        Stage currentStage = (Stage) newPasswordTF.getScene().getWindow();
                        currentStage.close();
                        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/loginUI.fxml"));
                        Parent loginRoot = loginLoader.load();
                        Stage loginStage = new Stage();
                        loginStage.setScene(new Scene(loginRoot));
                        loginStage.show();
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUserEmail(String email) {
        System.out.println(email);
        this.email =email;
    }
@FXML
    public void registerLinkOnClik(ActionEvent event){
        Node node=(Node) event.getSource() ;
        NavigationManager.loadView("/registerUI.fxml","register UI",node);
    }
}
