package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.RendezVousServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class RendezVousBack {

    @FXML
    private TableColumn<RendezVous, String> datecolumn;

    @FXML
    private TableColumn<RendezVous, Integer> heurecolumn;

    @FXML
    private TableColumn<RendezVous, String> nomcolumn;

    @FXML
    private TableView<RendezVous> rendezvoustable;

    private final RendezVousServices rendezvousServices;

    public RendezVousBack() {
        Connection connection = MaConnexion.getInstance().getCnx();
        rendezvousServices = new RendezVousServices(connection);
    }


    @FXML
    void initialize() {
        try {
            List<RendezVous> rendezVous = rendezvousServices.getAll();
            ObservableList<RendezVous> observableList = FXCollections.observableList(rendezVous);

            rendezvoustable.setItems(observableList);
            datecolumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation")); // Update to "dateReservation"
            heurecolumn.setCellValueFactory(new PropertyValueFactory<>("heure"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des rendez-vous.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguerEtab(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EtablissementBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void naviguermed(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MedcinBack.fxml"));
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

    @FXML
    void naviguerrendez(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RendezVousBack.fxml"));

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

}
