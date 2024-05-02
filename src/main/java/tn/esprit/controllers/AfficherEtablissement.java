package tn.esprit.controllers;

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
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;
import tn.esprit.controllers.AfficherMedcin;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AfficherEtablissement {

    @FXML
    private ListView<Etablissement> etablissementListView; // Make sure this matches the fx:id in the FXML file

    private final EtablissementServices etablissementServices;

    public AfficherEtablissement() {
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
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

                        // Create and configure the ImageView
                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);
                        Image image = new Image("drees_etablissements_de_sante_enquete_2860620_Drupal.jpg");
                        imageView.setImage(image);

                        // Set the rating control's value to the rating of the current establishment
                        ratingControl.setRating(item.getRating());

                        // Add the ImageView to the VBox
                        vbox.getChildren().add(imageView);

                        // Add other labels and button as before
                        nomLabel.setText("Nom: " + item.getNom());
                        typeLabel.setText("Type: " + item.getType());
                        localisationLabel.setText("Localisation: " + item.getLocalisation());
                        vbox.getChildren().addAll(nomLabel, typeLabel, localisationLabel, ratingControl, afficherMedcinsButton);
                        setGraphic(vbox);
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
//    private void saveRating(Etablissement etablissement, int rating) {
//        // Save the rating to the database or perform any other necessary actions
//        try {
//            etablissementServices.updateRating(etablissement.getId(), rating);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Succès");
//            alert.setContentText("Votre évaluation a été enregistrée avec succès.");
//            alert.showAndWait();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur");
//            alert.setContentText("Une erreur s'est produite lors de l'enregistrement de votre évaluation.");
//            alert.showAndWait();
//        }
//    }
private void saveRating(Etablissement etablissement, float rating) {
    // Enregistrer le rating dans la base de données
    try {
        etablissementServices.updateRating(etablissement.getId(), rating);
        // Mettre à jour le rating dans l'objet Etablissement
        etablissement.setRating(rating);
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
            controller.initData(etablissement); // Passer l'établissement sélectionné au contrôleur AfficherMedcin

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


    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherMedcin.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            // Gérer l'erreur de chargement de la vue
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    public void naviguermed(ActionEvent actionEvent) {
    }

    public void naviguerrendez(ActionEvent actionEvent) {
    }

    public void naviguerEtab(ActionEvent actionEvent) {
    }
}