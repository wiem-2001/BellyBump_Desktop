package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Produit;

import java.io.ByteArrayInputStream;

public class ProductItemController {

    @FXML
    private ImageView productImageView;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productPriceLabel;

    public void setProductData(Produit produit) {
        productNameLabel.setText(produit.getNom());
        productPriceLabel.setText(String.format("%.2f €", produit.getPrix()));
        // Suppose que getImageFX() renvoie une Image JavaFX
        // Si getImage() renvoie un byte[], vous devez le convertir en Image
        productImageView.setImage(new Image(new ByteArrayInputStream(produit.getImage())));
    }
}
