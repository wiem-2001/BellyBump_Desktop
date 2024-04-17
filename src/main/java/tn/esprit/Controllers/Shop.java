package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Produit;
import tn.esprit.services.ProduitServices;

import java.io.ByteArrayInputStream;
import java.util.List;

public class Shop {

    @FXML
    private ImageView productImageView;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productPriceLabel;

    public void initialize() {
        ProduitServices produitServices = new ProduitServices();
        List<Produit> produits = produitServices.getAll();

        // Vérifiez qu'il y a au moins un produit
        if (!produits.isEmpty()) {
            // Prenez le premier produit de la liste
            Produit produit = produits.get(0);
            productNameLabel.setText(produit.getNom());
            productPriceLabel.setText(String.format("%.2f €", produit.getPrix()));

            // Assurez-vous que la méthode getImage() renvoie un tableau d'octets pour l'image
            byte[] imageData = produit.getImage();
            if (imageData != null && imageData.length > 0) {
                Image image = new Image(new ByteArrayInputStream(imageData));
                productImageView.setImage(image);
            } else {
                // Optionnel: Définir une image par défaut si aucune image n'est trouvée
                productImageView.setImage(new Image("path/to/default/image.png")); // Chemin vers une image par défaut
            }
        }
    }
}
