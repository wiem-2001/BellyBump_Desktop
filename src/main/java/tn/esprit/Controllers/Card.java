package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.entities.CartItem;
import tn.esprit.entities.Produit;
import tn.esprit.interfaces.CartUpdateListener;
import tn.esprit.services.CartServices;

import java.io.InputStream;

public class Card {

    @FXML
    private Label Description;

    @FXML
    private Label NomProduct;

    @FXML
    private Label Prix;

    @FXML
    private HBox box;

    @FXML
    private ImageView ProductImage;

    @FXML
    private Button ajouterPanier;

    private CartUpdateListener cartUpdateListener;
    private Produit product;
    //private String[] colors = {"#F9EFF5", "#EDB2FE", "#B9AA8", "#FF5056"};



// j ai utilise convertToFileSystem path bich twali meli kenent c:eya/users/.... twali direct /images
    public void setData(Produit produit) {
        ////////
        if(produit == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = produit;
        //////////
        String imagePath = convertToFileSystemPath(produit.getImagePath());

        // Now imagePath should be a classpath-relative path like "/images/logo.jpg"
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (imagePath != null && is != null) {
            Image image = new Image(is);
            ProductImage.setImage(image);
        } else {
            // Log the error or set a default image if the path is null or the resource cannot be found
            System.out.println("Image resource not found: " + imagePath);
            // Consider setting a placeholder image here
        }
        //ajouterPanier.setOnAction(event -> handleAddToCart());
        NomProduct.setText(produit.getNom());
        Description.setText(produit.getDescription());
        Prix.setText(String.format("$%.2f", produit.getPrix())); // Assuming getPrix() returns a double
//////////////cart lyom//////
        //Button addButton = new Button("add To Cart");
        // Assurez-vous que `produit` n'est pas `null` ici
        if(produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }

// Définissez le UserData du bouton avec le nom du produit
        ajouterPanier.setUserData(produit.getNom());

        // Assurez-vous que cartUpdateListener est défini avant de l'utiliser dans l'action du bouton
        ajouterPanier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CartServices cartServices = CartServices.getInstance();
                cartServices.addProduct(product.getNom());

                // Assurez-vous que cartUpdateListener n'est pas null avant d'appeler onCartUpdate
                if (cartUpdateListener != null) {
                    cartUpdateListener.onCartUpdate();
                } else {
                    System.err.println("CartUpdateListener n'est pas initialisé dans Card.");
                }
            }
        });
        ////cart lyom///////////
    }

   /* private void handleAddToCart() {
        if(this.product == null) {
            System.out.println("Product is null, cannot add to cart");
            return;
        }
        CartServices cartService = CartServices.getInstance();
        cartService.addItem(new CartItem(this.product, 1));
        if (cartUpdateListener != null) {
            cartUpdateListener.onCartUpdate();
        }
    }*/


    public String convertToFileSystemPath(String imagePath) {
        if (imagePath != null && imagePath.startsWith("file:/")) {
            imagePath = imagePath.replace("file:/", ""); // Remove file protocol
            imagePath = imagePath.replace("\\", "/"); // Replace backslashes with forward slashes if on Windows
            int imagesIndex = imagePath.indexOf("/images/");
            if (imagesIndex != -1) {
                imagePath = imagePath.substring(imagesIndex); // Get the relative path
            }
        }
        return imagePath;
    }



    public void setCartUpdateListener(CartUpdateListener listener) {
        this.cartUpdateListener = listener;
    }


}
