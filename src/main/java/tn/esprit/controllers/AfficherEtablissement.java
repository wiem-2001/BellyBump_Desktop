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
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AfficherEtablissement {

    @FXML
    private TableColumn<Etablissement, String> TypeColumn;

    @FXML
    private TableView<Etablissement> etablissementTable;

    @FXML
    private TableColumn<Etablissement, String> localisationColumn;

    @FXML
    private TableColumn<Etablissement, String> nomColum;
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

            etablissementTable.setItems(observableList);
            nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des établissements.");
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
}
