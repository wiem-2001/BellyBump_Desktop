package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Medcin;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.RendezVousServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class AffichezRendezVous {
    private Medcin selectedMedcin;
    @FXML
    private ListView<RendezVous> rendezvousListView;

    private final RendezVousServices rendezvousServices;

    public AffichezRendezVous() {
        Connection connection = MaConnexion.getInstance().getCnx();
        rendezvousServices = new RendezVousServices(connection);
    }

    @FXML
    public void initialize() {
        chargerRendezVous();
        rendezvousListView.setCellFactory(param -> new ListCell<RendezVous>() {
            private final Label dateLabel = new Label();
            private final Label heureLabel = new Label();
            private final Label medecinLabel = new Label();

            @Override
            protected void updateItem(RendezVous rendezVous, boolean empty) {
                super.updateItem(rendezVous, empty);

                if (empty || rendezVous == null) {
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();
                    dateLabel.setText("Date: " + rendezVous.getDateReservation());
                    heureLabel.setText("Heure: " + rendezVous.getHeure());
//                    medecinLabel.setText("Médecin: " + rendezVous.getNom_med());
                    vbox.getChildren().addAll(dateLabel, heureLabel, medecinLabel);
                    setGraphic(vbox);
                }
            }
        });
    }

    private void chargerRendezVous() {
        try {
            List<RendezVous> rendezVous = rendezvousServices.getAll();
            ObservableList<RendezVous> observableList = FXCollections.observableArrayList(rendezVous);
            rendezvousListView.setItems(observableList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des rendez-vous.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRendezVous.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterRendezVous.fxml"));
            Parent root = loader.load();
            AjouterRendezVous ajouterRendezVous = loader.getController();
            ajouterRendezVous.initData(selectedMedcin); // Transmettre le médecin sélectionné
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

    public void initData(Medcin medcin) {
        try {
            List<RendezVous> rendezVous = rendezvousServices.getByNomMed(medcin.getNom());
            ObservableList<RendezVous> observableList = FXCollections.observableArrayList(rendezVous);
            rendezvousListView.setItems(observableList);
            selectedMedcin = medcin;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des rendez-vous.");
            alert.showAndWait();
        }
    }
    // Ajouter une méthode pour récupérer le médecin sélectionné
    public Medcin getSelectedMedcin() {
        return selectedMedcin;
    }
}