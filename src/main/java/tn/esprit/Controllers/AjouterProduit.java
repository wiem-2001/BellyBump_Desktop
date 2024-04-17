package tn.esprit.Controllers;

import javafx.fxml.FXML;
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
    private TextField TFDescription;

    @FXML
    private TextField TFNom;

    @FXML
    private TextField TFPrix;

    @FXML
    private TextField TFQuantité;

    @FXML
    private Button importImageButton;
    @FXML
    private ImageView imageview;

    private byte[] imageProduit;

    @FXML
    public void onImporterImageClicked() {
        Stage stage = (Stage) importImageButton.getScene().getWindow(); // Obtenir le Stage actuel
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                imageProduit = Files.readAllBytes(file.toPath());
                // Ici, vous pouvez ajouter du code pour afficher l'image dans un ImageView si vous en avez un
                imageview.setImage(new Image(new ByteArrayInputStream(imageProduit)));

            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception proprement
            }
        }
    }

    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "Ajouter"
    @FXML
    public void onAjouterClicked() {
        try {
            String nom = TFNom.getText();
            String description = TFDescription.getText();
            double prix = Double.parseDouble(TFPrix.getText());
            int quantite = Integer.parseInt(TFQuantité.getText());

            // Créez une instance de Produit avec l'image
            Produit produit = new Produit(nom, description, prix, quantite, imageProduit);

            // Appeler le service d'ajout de produit
            ProduitServices service = new ProduitServices();
            service.add(produit);

            // Afficher une alerte de confirmation
            showAlert("Succès", "Produit ajouté avec succès!", Alert.AlertType.INFORMATION);

            // Réinitialiser les champs de saisie et l'image
            TFNom.clear();
            TFPrix.clear();
            TFDescription.clear();
            TFQuantité.clear();
            imageview.setImage(null);
            imageProduit = null;

        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si les données numériques ne sont pas valides
            showAlert("Erreur de Format", "Veuillez entrer des nombres valides pour le prix et la quantité.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Gérer les autres exceptions éventuelles
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

}


