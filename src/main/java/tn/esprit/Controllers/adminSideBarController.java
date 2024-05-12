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
import tn.esprit.Controllers.coach.CoachController;
import tn.esprit.Controllers.event.AdminEventsList;
import tn.esprit.Controllers.event.ShowEvents;
import tn.esprit.Controllers.AfficherPartenaire;
import tn.esprit.Controllers.Dashbord;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminSideBarController implements Initializable {

    @FXML
    private HBox ManagePost;
    @FXML
    private HBox hospitalclick;
    @FXML
    private HBox appointmentClick;

    @FXML
    private HBox babyclique1;

    @FXML
    private HBox infauxclick;
    @FXML
    private HBox medcinclique;
    
    @FXML
    private VBox sidebar;
    @FXML
    private HBox coachTableViewClick;
    @FXML
    private HBox logoutLinkOnClick;
    @FXML
    private ImageView menuBack;

    @FXML
    private ImageView menu;
    @FXML
    private HBox manageUsersOnClick;

    @FXML
    private HBox eventTableViewClick;
    @FXML
    Text userEmailT;
    @FXML
    private BorderPane initialPage;
    @FXML
    private HBox manageProducts;
    @FXML
    private HBox AboutTheApp;

    @FXML
    private HBox ManagePartners;


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
        manageProducts.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/Dashbord.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        ManagePartners.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/AfficherPartenaire.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        hospitalclick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/EtablissementBack.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        appointmentClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/MedcinBack.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        medcinclique.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/RendezVousBack.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        babyclique1.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/AfficherBabyBack.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        infauxclick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/AfficherInfoMedicauxBack.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));

       AboutTheApp.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/Stat.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
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
        eventTableViewClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/event/TableOfEvents.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        coachTableViewClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/coach/coachTableView.fxml"));
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
                MainFX.setLoggedInUserEmail("");
                Stage stage= (Stage) logoutLinkOnClick.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        manageUsersOnClick.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/usersDashboard.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));

        ManagePost.setOnMouseClicked((mouseEvent -> {
            try{
                FXMLLoader loader2= new FXMLLoader();
                loader2.setLocation(getClass().getResource("/dash.fxml"));
                Parent root = loader2.load();
                initialPage.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));


    }
}
