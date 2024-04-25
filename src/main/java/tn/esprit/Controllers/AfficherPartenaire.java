package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPartenaire {

    @FXML
    private TableColumn<Partenaire, String> columnDescription;

    @FXML
    private TableColumn<Partenaire, String> columnEmail;

    @FXML
    private TableColumn<Partenaire, String> columnMarque;

    @FXML
    private TableColumn<Partenaire, String> columnNom;

    @FXML
    private Button submitButton;

    @FXML
    private TableView<Partenaire> tableViewPartenaires;
    private final PartenaireServices ps = new PartenaireServices();

    @FXML
    void handleSubmitButtonAction(ActionEvent event) {


    }

    @FXML
    public void initialize() {

        try {
            List<Partenaire> partenaires = ps.getAll();
            partenaires = ps.getAll();
            ObservableList<Partenaire> observableList = FXCollections.observableList(partenaires);
            tableViewPartenaires.setItems(observableList);
            columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            columnMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
            columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

        // columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        //columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        //columnMarque.setCellValueFactory(new PropertyValueFactory<>("marque"));

        //columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadPartenaireData();
    }


    private void loadPartenaireData() {
        PartenaireServices service = new PartenaireServices();
        ObservableList<Partenaire> partenaireObservableList = FXCollections.observableArrayList(service.getAll());
        tableViewPartenaires.setItems(partenaireObservableList);

    }


    @FXML
    private void handleDeletePartenaire() {
        Partenaire selectedPartenaire = tableViewPartenaires.getSelectionModel().getSelectedItem();
        if (selectedPartenaire != null) {
            // Code pour supprimer le partenaire de la base de données
           ps.delete(selectedPartenaire);
            // Actualiser la TableView
            tableViewPartenaires.getItems().remove(selectedPartenaire);
        } else {
            // Afficher un message d'erreur si aucun partenaire n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un partenaire à supprimer.");
            alert.showAndWait();
        }
    }
}