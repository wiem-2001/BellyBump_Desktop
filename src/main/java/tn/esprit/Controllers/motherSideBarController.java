package tn.esprit.Controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.MainFX;
import tn.esprit.Controllers.event.EventDetails;
import tn.esprit.Controllers.event.ShowEvents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmailT.setText(MainFX.getLoggedInUserEmail());

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
}
