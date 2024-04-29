package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.entities.Produit;
import tn.esprit.services.ProduitServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UpdateProduit {

    @FXML
    private TextField TFNom;

    @FXML
    private TextField TFPrix;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFPartenaireID;

    @FXML
    private ImageView imageView;

    private Produit selectedProduit;
    private final ProduitServices produitService = new ProduitServices();
    private String imagePath;

    public void initData(Produit produit) {
        this.selectedProduit = produit;
        TFNom.setText(produit.getNom());
        TFPrix.setText(String.valueOf(produit.getPrix()));
        TFDescription.setText(produit.getDescription());
        TFPartenaireID.setText(String.valueOf(produit.getPartenaireId()));
        // Supposer que la classe Produit a une méthode getImagePath() pour obtenir le chemin de l'image.
        imagePath = produit.getImagePath();
        // ... Charger l'image dans imageView si nécessaire.
    }

    @FXML
    void updateProduit(ActionEvent event) {
        selectedProduit.setNom(TFNom.getText());
        selectedProduit.setPrix(Double.parseDouble(TFPrix.getText()));
        selectedProduit.setDescription(TFDescription.getText());
        selectedProduit.setPartenaireId(Integer.parseInt(TFPartenaireID.getText()));

        if (imagePath != null && !imagePath.isEmpty()) {
            selectedProduit.setImagePath(imagePath);
        }
        produitService.update(selectedProduit);
        showAlert(Alert.AlertType.INFORMATION, "Modification réussie", null, "Les modifications ont été enregistrées avec succès");
    }

    @FXML
    void importImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image pour le produit");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String targetDirectory = "src/main/resources/Images/";
            try {
                Files.createDirectories(Path.of(targetDirectory)); // Plus sûr que mkdirs

                Path sourcePath = selectedFile.toPath();
                Path targetPath = Path.of(targetDirectory, selectedFile.getName());
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");

                // Afficher l'image dans l'interface utilisateur
                imageView.setImage(new javafx.scene.image.Image(new File(imagePath).toURI().toString()));

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Problème lors de l'importation de l'image", e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
