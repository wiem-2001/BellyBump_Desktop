package tn.esprit.Controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.MainFX;
import tn.esprit.Controllers.event.EventDetails;
import tn.esprit.Controllers.event.ShowEvents;
import tn.esprit.entities.User;
import tn.esprit.services.UserServices;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;

public class motherSideBarController implements Initializable {
    @FXML
    private HBox cartClick;

    @FXML
    private HBox dashpostClick;

    @FXML
    private HBox ShopClick;
    @FXML
    private HBox feedClick;

    @FXML
    private VBox sidebar;
    @FXML
    private HBox calendarClick;
    @FXML
    private ImageView menuBack;

    @FXML
    private ImageView menu;

    @FXML
    private HBox eventListClick;

    @FXML
    private HBox logoutLinkOnClick;

    @FXML
    private HBox tasksClick;

    @FXML
    private HBox updatepassword;

    @FXML
    private HBox userProfile;
    @FXML
    Text userEmailT;
    @FXML
    private BorderPane initialPage;
    @FXML
    ImageView profileImageView;
    UserServices us=new UserServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmailT.setText(MainFX.getLoggedInUserEmail());
        User user=us.getOne(MainFX.getLoggedInUserEmail());
   String imageName = user.getImage(); // Assuming it contains only the image name
        String imagePath = getUserImageDirectory() + imageName; // Concatenate directory and image name
        try {
            System.out.println(imageName);
            System.out.println(imagePath);
            File file = new File(imagePath);
            URL ImageUrl = file.toURI().toURL();
            Image image = new Image(ImageUrl.toString());
            profileImageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        sidebar.setTranslateX(0);
        menu.setVisible(false);
        menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sidebar);

            slide.setToX(0);
            slide.play();
            initialPage.setLeft(sidebar);
            sidebar.setTranslateX(-250);
            slide.setOnFinished(action ->{
                menu.setVisible(false);
                menuBack.setVisible(true);

            });

        });
        menuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sidebar);

            slide.setToX(-250);
            slide.play();
            sidebar.setTranslateX(0);
            slide.setOnFinished(action ->{
                menu.setVisible(true);
                menuBack.setVisible(false);
                initialPage.setLeft(null);
            });
        });

        eventListClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/showEvents.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
       /* dashpostClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/dash.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));*/
        cartClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/Cart.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));


       ShopClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/Shop.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        feedClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/Feed.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        calendarClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/CalendarFile.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        tasksClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/tasksUI.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        updatepassword.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/updatePasswordUi.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        userProfile.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/userProfilUI.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        logoutLinkOnClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/loginUI.fxml"));
                Parent root = loader2.load();
                Stage stage= (Stage) eventListClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public void setEventList()
    {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/showEvents.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void setTasksList()
    {
        try{
            FXMLLoader loader2= new FXMLLoader();
            loader2.setLocation(getClass().getResource("/tasksUI.fxml"));
            Parent root = loader2.load();
            initialPage.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getUserImageDirectory() {
        String imagesDirectory = "C:/Users/user/IdeaProjects/bellyBump_Desktop/src/main/resources/assets/images/";
        return imagesDirectory;
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
}
