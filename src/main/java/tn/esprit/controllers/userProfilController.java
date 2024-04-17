package tn.esprit.controllers;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.MainFX;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;
import tn.esprit.util.NavigationManager;


import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;


public class userProfilController {
    UserServices us=new UserServices();
    private String imageLink;
    @FXML
    TextField firstNameTF,lastNameTF,addressTF,phoneNumberTF;
    @FXML
    DatePicker birtdhayTF;
    @FXML
    Button updateProfilTF;
    @FXML
    Text userEmailT,errorBirthday,errorT;
    @FXML
    HBox updatePassB;
    @FXML
    ImageView profileImageView;

    public void initialize() {
        User user=us.getOne(MainFX.getLoggedInUserEmail());
        firstNameTF.setText(user.getFirst_name());
        lastNameTF.setText(user.getLast_name());
        addressTF.setText(user.getAdress());
        phoneNumberTF.setText(String.valueOf(user.getPhone_number()));
        String userBirthday=user.getBirthday().toString();
        LocalDate birthdayDate = LocalDate.parse(userBirthday);
        birtdhayTF.setValue(birthdayDate);
        userEmailT.setText(MainFX.getLoggedInUserEmail());
      //  String imagePath = user.getImage(); // Assuming getImage() returns the file path or URL as a string
      //  Image image = new Image(imagePath);
      //  profileImageView.setImage(image);

    }
    @FXML
    public void addImageButtonOnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            imageLink = selectedFile.toURI().toString();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Image");
            alert.setHeaderText("Do you want to save the selected image as your profile picture?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                // Update the user's profile image
                User currentUser = us.getOne(MainFX.getLoggedInUserEmail()) ;// Implement this method to get the current user
                if (currentUser != null) {
                    currentUser.setImage(imageLink); // Assuming setProfileImage method exists
                    // Call the method to update the user's profile image in the database
                    us.update(currentUser); // Assuming this method exists
                    // Inform the user that the image has been saved
                    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmationAlert.setTitle("Success");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Profile picture updated successfully!");
                    confirmationAlert.showAndWait();
                }
            }
        }
    }

    @FXML
    public void updateProfilOnAction(ActionEvent event) {
        String firstname = firstNameTF.getText();
        String lastname = lastNameTF.getText();
        String address = addressTF.getText();
        String email = userEmailT.getText();
        String phoneNumberText = phoneNumberTF.getText();
        LocalDate birthday = birtdhayTF.getValue();
        Date userbirthday = Date.valueOf(birtdhayTF.getValue().toString());
        String phoneNumberError = "";
        String birthdayError = "";
        if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || email.isEmpty() || phoneNumberText.isEmpty() || birthday == null) {
            errorT.setText("Please fill all the fields.");
        } else {
            if (!isValidPhoneNumber(phoneNumberTF.getText()) && !phoneNumberTF.getText().isEmpty()) {
                errorT.setText("Please enter a valid phone number (8 digits).");
            } else {
                errorT.setText("");
            }
            if (birthday.getYear() >= 2010) {
                errorBirthday.setText("Only birthdays before 2010 are accepted.");
            } else {
                errorBirthday.setText("");
            }
            if (errorT.getText().isEmpty() && errorBirthday.getText().isEmpty()) {
                try {
                    int phoneNumber = Integer.parseInt(phoneNumberText);
                    User user = new User(email, firstname, lastname, address, userbirthday, phoneNumber);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationDialog.fxml"));
                    Parent root = loader.load();
                    confirmationDialogController controller = loader.getController();
                    controller.setUser(user);
                    Stage confirmationStage = new Stage();
                    confirmationStage.initModality(Modality.APPLICATION_MODAL);
                    confirmationStage.setScene(new Scene(root));
                    confirmationStage.showAndWait();
                    if (controller.isConfirmed()) {
                        us.update(user);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error: Please enter a valid integer for the phone number.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean isValidPhoneNumber (String phoneNumber){
        return phoneNumber.matches("\\d{8}");
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
    public void userProfilLinkOnClick(ActionEvent event) {
        NavigationManager.navigateToUserProfil(userEmailT);
    }
    @FXML
    public void navigateToTasksOnClick(ActionEvent event) {
        Node node=(Node) event.getSource() ;
        NavigationManager.navigateToTasksView(node);
    }
}




