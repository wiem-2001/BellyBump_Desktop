package tn.esprit.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tn.esprit.MainFX;
import tn.esprit.entities.Etablissement;
import tn.esprit.entities.User;
import tn.esprit.services.EtablissementServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.MaConnexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AfficherEtablissement {

    @FXML
    private ListView<Etablissement> etablissementListView;

    private final EtablissementServices etablissementServices;
    private final Map<Integer, RatingController> ratingControllers;

    public AfficherEtablissement() {
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
        ratingControllers = new HashMap<>();
    }

    @FXML
    void initialize() {
        try {
            List<Etablissement> etablissements = etablissementServices.getAll();
            ObservableList<Etablissement> observableList = FXCollections.observableList(etablissements);

            etablissementListView.setItems(observableList);

            etablissementListView.setCellFactory(param -> new ListCell<Etablissement>() {
                private final Button afficherMedcinsButton = new Button("Afficher les médecins");
                private final Label nomLabel = new Label();
                private final Label typeLabel = new Label();
                private final Label localisationLabel = new Label();
                private final Rating ratingControl = new Rating();

                {
                    afficherMedcinsButton.setOnAction(event -> {
                        Etablissement etablissement = getItem();
                        if (etablissement != null) {
                            naviguerVersAfficherMedcin(etablissement);
                        }
                    });
                    ratingControl.setOnMouseClicked(event -> {
                        Etablissement etablissement = getItem();
                        if (etablissement != null) {
                            int newRating = (int) ratingControl.getRating();
                            saveRating(etablissement, newRating);
                        }
                    });
                }

                @Override
                protected void updateItem(Etablissement item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        VBox vbox = new VBox();
                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        Image image = new Image("drees_etablissements_de_sante_enquete_2860620_Drupal.jpg");
                        imageView.setImage(image);

                        ratingControl.setRating(item.getRating());

                        vbox.getChildren().add(imageView);

                        nomLabel.setText("Nom: " + item.getNom());
                        typeLabel.setText("Type: " + item.getType());
                        localisationLabel.setText("Localisation: " + item.getLocalisation());
                        vbox.getChildren().addAll(nomLabel, typeLabel, localisationLabel, ratingControl, afficherMedcinsButton);
                        setGraphic(vbox);
                        final UserServices us = new UserServices();
                        String userEmail= MainFX.getLoggedInUserEmail();
                        User user=us.getOne(userEmail);
                        RatingController ratingController = ratingControllers.computeIfAbsent(item.getId(), id -> new RatingController(item.getId(), user.getId()));
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des établissements.");
            alert.showAndWait();
        }
    }

    private void saveRating(Etablissement etablissement, int rating) {
        RatingController ratingController = ratingControllers.get(etablissement.getId());
        ratingController.addRating(rating);
        float averageRating = ratingController.calculateAverageRating();

        try {
            etablissementServices.updateRating(etablissement.getId(), averageRating);
            etablissement.setRating(averageRating);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Votre évaluation a été enregistrée avec succès.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de l'enregistrement de votre évaluation.");
            alert.showAndWait();
        }
    }

    private void naviguerVersAfficherMedcin(Etablissement etablissement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMedcin.fxml"));
            Parent root = loader.load();
            AfficherMedcin controller = loader.getController();
            controller.initData(etablissement);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }



}
