package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.RendezVousServices;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

import static java.awt.List.*;

public class AjouterRendezVous {

    @FXML
    private DatePicker dateReservation;

    @FXML
    private TextField heure;

    private final RendezVousServices ps = new RendezVousServices();

    @FXML
    void AjouterRendezVous(ActionEvent event) {


        try {
            LocalDate date = dateReservation.getValue(); // Correction : Utilisation de dateReservation.getValue()
             int H =Integer.parseInt(heure.getText());
            RendezVous nouveauRendezVous = new RendezVous(date, H);
            ps.add(nouveauRendezVous);
            System.out.println("Rendez-vous ajouté avec succès !");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEtablissement.fxml"));
            Parent root = loader.load();
            AfficherEtablissement listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle(" Rendez-vous");
            listStage.show();
        } catch (Exception e) {
//            System.err.println("Erreur lors de l'ajout du rendez-vous : " + e.getMessage());
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Erreur lors de l'ajout du rendez-vous : " + e.getMessage());
//            alert.showAndWait();
        }
    }


    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEtablissement.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
