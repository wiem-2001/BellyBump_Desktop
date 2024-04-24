package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.entities.Medcin;
import tn.esprit.services.EtablissementServices;
import tn.esprit.services.MedcinServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class AjouterMedcin {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField specialite;
    private EtablissementServices etablissementServices;

    @FXML
    private ComboBox<String> etablissementComboBox;
@FXML
    private void initialize() {
        // Assuming you have a database connection available
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
        List<String> etablissementNames = etablissementServices.getAllEtablissementNames();
        etablissementComboBox.getItems().addAll(etablissementNames);
    }


    @FXML
    void ajouterMedcin(ActionEvent event) {
        try {
            String nomMedcin = nom.getText();
            String prenomMedcin = prenom.getText();
            String specialiteMedcin = specialite.getText();
            String  selectedEtablissement = etablissementComboBox.getValue();

            // Vérifiez si tous les champs sont remplis
            if (nomMedcin.isEmpty() || prenomMedcin.isEmpty() || specialiteMedcin.isEmpty() || selectedEtablissement == null) {
                // Affichez un message d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return; // Sortez de la méthode
            }

            // Créez une instance de votre service MedcinServices et ajoutez le médecin
            Medcin newMedcin = new Medcin(nomMedcin, prenomMedcin, specialiteMedcin);
            MedcinServices medcinServices = new MedcinServices();
            medcinServices.add(newMedcin, selectedEtablissement);

            // Affichez un message de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Médecin ajouté avec succès !");
            successAlert.showAndWait();

            // Effacez les champs après l'ajout réussi
            nom.clear();
            prenom.clear();
            specialite.clear();

        } catch (Exception e) {
            // Gérez les exceptions si nécessaire
            e.printStackTrace();
        }
    }

    // Autres méthodes de navigation...


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
