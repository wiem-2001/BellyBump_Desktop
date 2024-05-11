package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class updateProfilController {
    UserServices us=new UserServices();
    @FXML
    TextField firstNameTF,lastNameTF,addressTF,phoneNumberTF;
    @FXML
    DatePicker birtdhayTF;
    @FXML
    Button updateProfilTF;
    @FXML
    Text emailTF;

    public void updateProfilOnAction(){
        String firstname = firstNameTF.getText();
        String lastname = lastNameTF.getText();
        String address = addressTF.getText();
        Integer phoneNumber = Integer.parseInt(phoneNumberTF.getText());

        // Convert LocalDate to Date
        LocalDate localDate = birtdhayTF.getValue();
        Date birthday = localDate != null ? Date.valueOf(localDate) : null;

        String email = emailTF.getText();

        User user = new User(email, firstname, lastname, address, birthday, phoneNumber);
        us.update(user);
    }
}
