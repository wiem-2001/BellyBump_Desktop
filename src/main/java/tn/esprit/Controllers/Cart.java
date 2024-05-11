package tn.esprit.Controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.CartItem;
import tn.esprit.entities.Produit;
import tn.esprit.interfaces.CartUpdateListener;
import tn.esprit.services.CartServices;

import java.io.IOException;
import java.util.List;
import tn.esprit.services.InvoiceGenerator;

public class Cart {
     @FXML
     private VBox cartPane;


    @FXML
    private Label TVALabel;


    @FXML
    private Label totalLabel;


    @FXML
    private Label totalandTVA;
    private double total = 0.0; //pour stocker le total

    private double tvaRate = 0.2; // Taux de TVA (par exemple, 20%)

    public void initialize(){
//populate the view int id, String nom, String description, Double prix, String imagePath, int partenaire_id

        manageCart();
     }

     public void manageCart()
     {
         List<CartItem> entries =CartServices.getInstance().getEntries();

         cartPane.getChildren().clear();
         if (entries.isEmpty())
         {
             Label emptyLabel=new Label("Empty cart");
             cartPane.getChildren().add(emptyLabel);
         }else {
             Label CartTitle = new Label("Shopping Cart");
             cartPane.getChildren().add(CartTitle);
             for (CartItem cartItem : entries) {
                 HBox hBox = new HBox(30); // Ajoutez de l'espacement entre les éléments
                 hBox.setPadding(new Insets(30)); // Ajoutez du padding autour du HBox

                 // Créez et configurez l'ImageView
                 ImageView imageView = new ImageView();
                 String imagePath = convertToFileSystemPath(cartItem.getProduct().getImagePath());
                 Image image = new Image(getClass().getResourceAsStream(imagePath));
                 imageView.setImage(image);
                 imageView.setFitHeight(100); // Configurez la hauteur selon votre UI
                 imageView.setFitWidth(100); // Configurez la largeur selon votre UI

                 // Créez le reste des labels pour le nom du produit, la quantité et le prix
                 Label productName = new Label(cartItem.getProduct().getNom());
                 Label productQuantity = new Label("Quantité: " + cartItem.getQuantity());
                 Label productPrice = new Label(String.format("Prix: $%.2f", cartItem.getProduct().getPrix()));

                 //**   // Ajoutez le prix de ce produit au total
                 total += cartItem.getTotalPrice();
                 ////////
                 // Créer le bouton pour augmenter la quantité
                 // Bouton pour augmenter la quantité
                 Button increaseButton = new Button("+");
                 increaseButton.setOnAction(e -> {
                     cartItem.increaseQuantity(); // Augmente la quantité dans le modèle
                     productQuantity.setText(String.valueOf(cartItem.getQuantity())); // Mise à jour de l'affichage de la quantité
                     productPrice.setText(String.format("Prix: %.2f", cartItem.getProduct().getPrix())); // Mise à jour du prix total
                     updateTotal(); // Mise à jour du total affiché
                 });

                 // Bouton pour diminuer la quantité
                 Button decreaseButton = new Button("-");
                 decreaseButton.setOnAction(e -> {
                     cartItem.decreaseQuantity(); // Diminue la quantité dans le modèle
                     productQuantity.setText(String.valueOf(cartItem.getQuantity())); // Mise à jour de l'affichage de la quantité
                     productPrice.setText(String.format("Prix: %.2f", cartItem.getProduct().getPrix() * cartItem.getQuantity())); // Mise à jour du prix total
                     if (cartItem.getQuantity() <= 0) {
                         cartPane.getChildren().remove(hBox); // Suppression du produit de l'affichage
                         //CartServices.getInstance().removeProduct(cartItem.getProduct()); // Suppression du produit du service de panier
                     }
                     updateTotal(); // Mise à jour du total affiché
                 });

                 ///////

                 // Ajoutez tous les éléments au HBox
                 hBox.getChildren().addAll(imageView, productName, increaseButton ,productQuantity, decreaseButton ,productPrice  );

                 // Ajoutez le HBox au cartPane
                 cartPane.getChildren().add(hBox);

             }

             // Affichez le total
             // Calculez le montant de la TVA
             double tvaAmount = total * tvaRate;
            // total += tvaAmount; // Ajoutez le montant de la TVA au total

           /*  // Affichez le total avec la TVA
             Label totalLabel = new Label(String.format("Total produits ($%.2f)", total));
             cartPane.getChildren().add(totalLabel);
             Label TVAlLabel = new Label(String.format("TVA  : $%.2f ", tvaAmount));
             cartPane.getChildren().add(TVAlLabel);
             Label totalTVALabel = new Label(String.format("Total avec TVA: $%.2f ", total + tvaAmount));
             cartPane.getChildren().add(totalTVALabel);*/
             updateTotal();
             //****************************


             //////////////******************
         }



     }
    public void pdf() {
        List<CartItem> cartItems = CartServices.getInstance().getEntries();
        double total = calculateTotal(cartItems); // Call your implemented method to calculate total
        double tvaAmount = total * this.tvaRate; // Calculate the TVA based on the total

        // Now, call the static method 'generateInvoice' from the 'InvoiceGenerator' class
        // Ensure that the 'generateInvoice' method is indeed static and accepts the parameters as below
        try {
            InvoiceGenerator.generateInvoice("Facture.pdf", cartItems, total, tvaAmount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private double calculateTotal(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrix();
        }
        return total;
    }








    public String convertToFileSystemPath(String imagePath) {
        if (imagePath != null && imagePath.startsWith("file:/")) {
            imagePath = imagePath.replace("file:/", ""); // Supprimez le protocole de fichier
            imagePath = imagePath.replace("\\", "/"); // Remplacez les barres obliques inversées par des barres obliques directes si vous êtes sur Windows
            int imagesIndex = imagePath.indexOf("/images/");
            if (imagesIndex != -1) {
                imagePath = imagePath.substring(imagesIndex); // Obtenez le chemin relatif
            }
        }
        return imagePath;
    }




    @FXML
    void sliderArrow() {
       /* TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-224);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(-224 + 90);



        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(false);
            bars_btn.setVisible(true);

        });


        slide1.play();
        slide.play();*/

    }


    @FXML
    void sliderBars() {
       /* TranslateTransition slide = new TranslateTransition();

        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        TranslateTransition slide1 = new TranslateTransition();

        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(0);



        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(true);
            bars_btn.setVisible(false);

        });


        slide1.play();
        slide.play();*/

    }

    @FXML
    public void onButtonClickAfficher(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Shop.fxml"));
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



    // Méthode pour mettre à jour le total dans le panier
    public void updateTotal() {
        // Calculez le total à partir des éléments du panier
        total = calculateTotal(CartServices.getInstance().getEntries());

        // Calculez la TVA sur la base du total
        double tvaAmount = total * tvaRate;

        // Calculez le total incluant la TVA
        double totalWithTVA = total + tvaAmount;

        // Mettez à jour les labels avec les valeurs calculées
        totalLabel.setText(String.format("Total: $%.2f", total));
        TVALabel.setText(String.format("TVA: $%.2f", tvaAmount));
        totalandTVA.setText(String.format("Total avec TVA: $%.2f", total + tvaAmount));
    }




    //payment////
    @FXML
    private void goToPayment() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/payement.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the current instance
            Payment paymentController = loader.getController();
            paymentController.setCartController(this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Payment");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ClearCart(){
        cartPane.getChildren().clear();
    }

}