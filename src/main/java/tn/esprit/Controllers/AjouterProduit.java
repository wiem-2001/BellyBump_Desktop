package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Produit;
import tn.esprit.services.ProduitServices;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AjouterProduit {

    @FXML
    private TextField TFNom;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFPrix;

    @FXML
    private TextField TFPartenaireID; // Ajout d'un champ pour saisir l'ID du partenaire

    @FXML
    private Button importImageButton;

    @FXML
    private ImageView imageview;

    private String imagePath; // Remplacer le tableau d'octets par un chemin d'image

    @FXML
    public void onImporterImageClicked() {
        Stage stage = (Stage) importImageButton.getScene().getWindow(); // Obtenir le Stage actuel
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imagePath = file.toURI().toString(); // Stocker le chemin de l'image
            imageview.setImage(new Image(imagePath));
        }
    }

    @FXML
    public void onAjouterClicked() {
        try {
            String nom = TFNom.getText();
            String description = TFDescription.getText();
            double prix = Double.parseDouble(TFPrix.getText());
            int partenaireID = Integer.parseInt(TFPartenaireID.getText()); // Récupérer l'ID du partenaire

            Produit produit = new Produit(nom, description, prix, imagePath, partenaireID); // Création d'un produit avec l'ID du partenaire

            ProduitServices service = new ProduitServices();
            service.add(produit); // Ajouter le produit à la base de données

            showAlert("Succès", "Produit ajouté avec succès!", Alert.AlertType.INFORMATION);

            TFNom.clear();
            TFPrix.clear();
            TFDescription.clear();
            TFPartenaireID.clear(); // Réinitialiser le champ partenaire
            imageview.setImage(null); // Réinitialiser l'image affichée
            imagePath = null; // Réinitialiser le chemin de l'image

        } catch (NumberFormatException e) {
            showAlert("Erreur de Format", "Veuillez entrer des nombres valides pour le prix et l'ID du partenaire.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du produit: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void onButtonClickshowPartner(ActionEvent event) {
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




