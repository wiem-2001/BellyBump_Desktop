package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static tn.esprit.services.UserServices.isValidEmail;

public class registerController {
    UserServices us = new UserServices();


    @FXML
    Button nextBT;
    @FXML
    TextField firstNameTF, lastNameTF, addressTF, phoneNumberTF,emailTF;
    @FXML
    PasswordField passwordTF,confirmPasswordTF;

    @FXML
    DatePicker birtdhayTF;
    @FXML
    Text errorTF, phoneNumberET, birthdayET,confirmPasswordET,passwordET,emailET;
    @FXML
    Button signUpButton;

    @FXML
    public void signUpButtonOnClick(ActionEvent event) {
        String firstname = firstNameTF.getText();
        String lastname = lastNameTF.getText();
        String phoneNumber = phoneNumberTF.getText();
        String address=addressTF.getText();
        String email=emailTF.getText();
        String password=passwordTF.getText();
        String confirmPassword=confirmPasswordTF.getText();
        LocalDate selectedDate = birtdhayTF.getValue();
        Date myDate = null;
        if (selectedDate != null) {
            myDate = Date.valueOf(selectedDate.toString());
        }
        if (firstname.isEmpty() || lastname.isEmpty()||myDate == null || phoneNumber.isEmpty() || address.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()||email.isEmpty()) {
            errorTF.setText("Please fill all the fields.");
        } else {
            errorTF.setText("");
            if (!isValidPhoneNumber(phoneNumber)&&!phoneNumber.isEmpty()) {
                phoneNumberET.setText("Please enter a valid phone number (8 digits).");
            } else {
                phoneNumberET.setText("");
            }
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
                passwordET.setText("Password must contain at least 6 characters \n (uppercase letters,lowercase letters, and special characters.)");
            }
            else {
                passwordET.setText("");
            }
            if (!isValidEmail(email)) {
                emailET.setText("Please enter a valid email address.");
            }   else {
                emailET.setText("");
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordET.setText("Passwords do not match.");
            }
            else {
                confirmPasswordET.setText("");
            }

            if(us.userExists(email))
            {
                errorTF.setText("User with the provided email already exists.");
            }
            if (myDate.getYear() >= 2010) {
                birthdayET.setText("Only birthdays before 2010 are accepted.");
            } else {
                birthdayET.setText("");
            }
        }


        if(errorTF.getText().isEmpty()&&passwordET.getText().isEmpty()&&emailET.getText().isEmpty()&&confirmPasswordET.getText().isEmpty()&&phoneNumberET.getText().isEmpty()&&birthdayET.getText().isEmpty())
        {
            String image="image";

            User user=new User(email,password,firstname,lastname,address,image,myDate,Integer.parseInt(phoneNumber));
            us.add(user);

            System.out.println("user added succefully");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginUI.fxml"));
                Parent loginUI = loader.load();

                // Get the stage from the event's source
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene loginScene = new Scene(loginUI);
                stage.setScene(loginScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void loginLinkOnClick(ActionEvent event) {
        try {
            // Load the register screen's FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginUI.fxml"));
            Parent registerUI = loader.load();

            // Get the stage from the event's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the register screen as the root of the scene
            Scene registerScene = new Scene(registerUI);

            // Set the register scene on the stage
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidPhoneNumber (String phoneNumber){
        return phoneNumber.matches("\\d{8}");
    }


}

