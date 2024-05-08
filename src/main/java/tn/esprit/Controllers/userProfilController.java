package tn.esprit.Controllers;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


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
    ImageView profileImageView;


    public void initialize() {


        User user = us.getOne(MainFX.getLoggedInUserEmail());
        firstNameTF.setText(user.getFirst_name());
        lastNameTF.setText(user.getLast_name());
        addressTF.setText(user.getAdress());
        phoneNumberTF.setText(String.valueOf(user.getPhone_number()));
        String userBirthday = user.getBirthday().toString();
        LocalDate birthdayDate = LocalDate.parse(userBirthday);
        birtdhayTF.setValue(birthdayDate);

       /* String imageName = user.getImage(); // Assuming it contains only the image name
        String imagePath = getUserImageDirectory() + imageName; // Concatenate directory and image name
        try {
            System.out.println(imageName);
            System.out.println(imagePath);
            File file = new File(imagePath);
            URL url = file.toURI().toURL();
            Image image = new Image(url.toString());
            profileImageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
    }

    public String getUserImageDirectory() {
        String imagesDirectory = "C:/Users/user/IdeaProjects/bellyBump_Desktop/src/main/resources/assets/images/";
        return imagesDirectory;
    }

    @FXML
    public void updateProfilOnAction(ActionEvent event) {
        String firstname = firstNameTF.getText();
        String lastname = lastNameTF.getText();
        String address = addressTF.getText();
        String phoneNumberText = phoneNumberTF.getText();
        LocalDate birthday = birtdhayTF.getValue();
        Date userbirthday = Date.valueOf(birtdhayTF.getValue().toString());
        String phoneNumberError = "";
        String birthdayError = "";
        System.out.println(birthday);
        System.out.println(userbirthday);
        if (firstname.isEmpty() || lastname.isEmpty() || address.isEmpty() || phoneNumberText.isEmpty() || birthday == null) {
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
                    User user = new User(MainFX.getLoggedInUserEmail(), firstname, lastname, address, userbirthday, phoneNumber);
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
    private String generateUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        return uuid + "." + fileExtension;
    }
    @FXML
    private void handleUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Generate a unique filename for the selected file
            String originalFileName = selectedFile.getName();
            String uniqueFileName = generateUniqueFileName(originalFileName);

            // Get the path to the destination directory
            String destinationDirectoryPath = "C:/Users/user/IdeaProjects/bellyBump_Desktop/src/main/resources/assets/images";

            // Create the destination directory if it doesn't exist
            Path destinationDirectory = Paths.get(destinationDirectoryPath);
            if (!Files.exists(destinationDirectory)) {
                try {
                    Files.createDirectories(destinationDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            // Create the destination file path
            Path destinationFilePath = destinationDirectory.resolve(uniqueFileName);

            // Copy the selected file to the destination directory with the unique filename
            try {
                Files.copy(selectedFile.toPath(), destinationFilePath);
                System.out.println("File uploaded successfully. Unique filename: " + uniqueFileName);

                // Update profile image in the UI
                reloadProfileImage(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // Update profile image in the database
            us.updateProfilImage(uniqueFileName.toString(), MainFX.getLoggedInUserEmail());
        }
    }
    private void reloadProfileImage(String imageName) {
        String imagePath = getUserImageDirectory() + imageName; // Concatenate directory and image name
        try {
            File file = new File(imagePath);
            URL url = file.toURI().toURL();
            Image image = new Image(url.toString());
            profileImageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidPhoneNumber (String phoneNumber){
        return phoneNumber.matches("\\d{8}");
    }
}




