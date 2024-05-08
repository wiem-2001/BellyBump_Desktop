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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.entities.Medcin;
import tn.esprit.services.MedcinServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AfficherMedcin {
    private Medcin selectedMedcin;
    @FXML
    private ListView<Medcin> medecinListView;

    private final MedcinServices medcinServices;
    private Etablissement etablissement;

    public AfficherMedcin() {
        Connection connection = MaConnexion.getInstance().getCnx();
        medcinServices = new MedcinServices(connection);
    }

    @FXML
    void initialize() {
        chargerMedcinsAssocies();
        medecinListView.setCellFactory(param -> new ListCell<Medcin>() {
            private final Button afficherRendezVousButton = new Button("Afficher rendez-vous");
            private final Label nomLabel = new Label();
            private final Label prenomLabel = new Label();
            private final Label specialiteLabel = new Label();

            {
                afficherRendezVousButton.setOnAction(event -> {
                    Medcin medcin = getItem();
                    if (medcin != null) {
                        naviguerRendezVous(medcin);
                    }
                });
            }

            @Override
            protected void updateItem(Medcin item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();
                    nomLabel.setText("Nom: " + item.getNom());
                    prenomLabel.setText("Prénom: " + item.getPrenom());
                    specialiteLabel.setText("Spécialité: " + item.getSpecialite());
                    vbox.getChildren().addAll(nomLabel, prenomLabel, specialiteLabel, afficherRendezVousButton);
                    setGraphic(vbox);
                }
            }
        });
    }

    private void chargerMedcinsAssocies() {
        try {
            if (etablissement != null) {
                List<Medcin> medcins = medcinServices.getByNomEtab(etablissement.getNom());
                ObservableList<Medcin> observableList = FXCollections.observableList(medcins);

                medecinListView.setItems(observableList);

                medecinListView.setOnMouseClicked(event -> {
                    Medcin medcin = medecinListView.getSelectionModel().getSelectedItem();
                    if (medcin != null) {
                        naviguerRendezVous(medcin);
                    }
                });
//            } else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Erreur");
//                alert.setContentText("Aucun établissement sélectionné.");
//                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des médecins.");
            alert.showAndWait();
        }
    }

    public void initData(Etablissement etablissement) {
        this.etablissement = etablissement;
        chargerMedcinsAssocies();
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEtablissement.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    public void naviguerRendezVous(Medcin medcin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichezRendezVous.fxml"));
            Parent root = loader.load();
            AffichezRendezVous controller = loader.getController();
            controller.initData(medcin);

            Stage window = (Stage) medecinListView.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue des rendez-vous.");
            alert.showAndWait();
        }
    }


}