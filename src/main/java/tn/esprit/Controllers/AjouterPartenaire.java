package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;
import javafx.event.ActionEvent;  // Correction de l'import pour utiliser JavaFX ActionEvent

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterPartenaire implements Initializable {

    @FXML
    private Button BADD;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFEmail;

    @FXML
    private TextField TFMarque;

    @FXML
    private TextField TFNom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation code can be placed here
    }

    @FXML
    public void handleAddButtonAction() {
        String nom = TFNom.getText();
        String marque = TFMarque.getText();
        String email = TFEmail.getText();
        String description = TFDescription.getText();

        Partenaire partenaire = new Partenaire(nom, marque, email, description);
        PartenaireServices service = new PartenaireServices();
        service.add(partenaire);

        System.out.println("Partenaire ajouté avec succès!");
        TFNom.clear();
        TFMarque.clear();
        TFEmail.clear();
        TFDescription.clear();
    }

    @FXML
    public void onButtonClickAfficher(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
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


}
