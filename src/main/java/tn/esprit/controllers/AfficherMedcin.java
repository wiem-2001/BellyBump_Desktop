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
import tn.esprit.entities.Medcin;
import tn.esprit.services.MedcinServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class AfficherMedcin {

    @FXML
    private TableView<Medcin> medcinView;

    @FXML
    private TableColumn<Medcin, String> nomColumn;

    @FXML
    private TableColumn<Medcin, String> prenomColumn;

    @FXML
    private TableColumn<Medcin, String> specialitéColumn;

    private final MedcinServices medcinServices;

    public AfficherMedcin() {
        Connection connection = MaConnexion.getInstance().getCnx();
        medcinServices = new MedcinServices(connection);
    }

    @FXML
    void initialize() {
        try {
            List<Medcin> medcins = medcinServices.getAll();
            ObservableList<Medcin> observableList = FXCollections.observableList(medcins);

            medcinView.setItems(observableList);
            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            specialitéColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des médecins.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichezRendezVous.fxml"));

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
