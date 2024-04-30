package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;
import java.sql.Date;
import java.time.LocalDate;
import static tn.esprit.services.UserServices.isValidEmail;
public class registerController {
    UserServices us = new UserServices();
    @FXML
    TextField firstNameTF, lastNameTF, addressTF, phoneNumberTF,emailTF;
    @FXML
    PasswordField passwordTF,confirmPasswordTF;

    @FXML
    DatePicker birthdayTF;
    @FXML
    Text errorTF, phoneNumberET, birthdayET,confirmPasswordET,passwordET,emailET;
   @FXML
        public void signUpButtonOnClick(ActionEvent event) {
            String firstname = firstNameTF.getText();
            String lastname = lastNameTF.getText();
            String phoneNumber = phoneNumberTF.getText();
            String address=addressTF.getText();
            String email=emailTF.getText();
            String password=passwordTF.getText();
            String confirmPassword=confirmPasswordTF.getText();
            LocalDate selectedDate = birthdayTF.getValue();
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
                if (selectedDate.getYear() >= 2010) {
                    birthdayET.setText("Only birthdays before 2010 are accepted.");
                } else {
                    birthdayET.setText("");
                }
            }
            if(errorTF.getText().isEmpty()&&passwordET.getText().isEmpty()&& emailET.getText().isEmpty()&&confirmPasswordET.getText().isEmpty()&&phoneNumberET.getText().isEmpty()&&birthdayET.getText().isEmpty())
            {
                User user=new User(email,password,firstname,lastname,address,myDate,Integer.parseInt(phoneNumber));
                System.out.println(user.toString());
               us.add(user);
                System.out.println("user added succefully");
                Node node=(Node) event.getSource() ;
                NavigationManager.navigateToLogin(node);
            }
        }
    @FXML
    public void loginLinkOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToLogin(node);
        }
    private boolean isValidPhoneNumber (String phoneNumber){
        return phoneNumber.matches("\\d{8}");
    }
    @FXML
    public void getBackToLogin (MouseEvent event)
    {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToLogin(node);
    }

}

