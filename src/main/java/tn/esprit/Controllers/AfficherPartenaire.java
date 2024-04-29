package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;

import java.io.IOException;
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


    @FXML
    public void onButtonClickAdd(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPartenaire.fxml"));
            Parent root = loader.load();

            // Obtient la scène actuelle et prépare la nouvelle scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Change la scène sur le même stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onButtonClickAddProduct(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();

            // Obtient la scène actuelle et prépare la nouvelle scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Change la scène sur le même stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onButtonClickAfficheProd(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashbord.fxml"));
            Parent root = loader.load();

            // Obtient la scène actuelle et prépare la nouvelle scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Change la scène sur le même stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void handleUpdateButtonAction(ActionEvent event) {
        Partenaire selectedPartenaire = tableViewPartenaires.getSelectionModel().getSelectedItem();
        if (selectedPartenaire != null) {
            // Ouvrir la fenêtre de mise à jour et passer les données sélectionnées
            openUpdatePartenaireWindow(selectedPartenaire);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
           // alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void openUpdatePartenaireWindow(Partenaire selectedPartenaire) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatePartenaire.fxml"));
            Parent root = loader.load();

            UpdatePartenaire controller = loader.getController();
            controller.initData(selectedPartenaire);

            Stage stage = new Stage();
            stage.setTitle("Mise à jour Partenaire");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}