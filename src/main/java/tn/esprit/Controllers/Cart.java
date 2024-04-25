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

public class Cart {
     @FXML
     private VBox cartPane;
     public void initialize(){
//populate the view int id, String nom, String description, Double prix, String imagePath, int partenaire_id

         List<CartItem> entries =CartServices.getInstance().getEntries();

         cartPane.getChildren().clear();
         if (entries.isEmpty())
         {
             Label emptyLabel=new Label("Empty cart");
             cartPane.getChildren().add(emptyLabel);
         }else
         {
             Label CartTitle =new Label("Shopping Cart");
             cartPane.getChildren().add(CartTitle);
             for (CartItem cartItem : entries) {
                 HBox hBox = new HBox(10); // Ajoutez de l'espacement entre les éléments
                 hBox.setPadding(new Insets(5)); // Ajoutez du padding autour du HBox

                 // Créez et configurez l'ImageView
                 ImageView imageView = new ImageView();
                 String imagePath = convertToFileSystemPath(cartItem.getProduct().getImagePath());
                 Image image = new Image(getClass().getResourceAsStream(imagePath));
                 imageView.setImage(image);
                 imageView.setFitHeight(50); // Configurez la hauteur selon votre UI
                 imageView.setFitWidth(50); // Configurez la largeur selon votre UI

                 // Créez le reste des labels pour le nom du produit, la quantité et le prix
                 Label productName = new Label(cartItem.getProduct().getNom());
                 Label productQuantity = new Label("Quantité: " + cartItem.getQuantity());
                 Label productPrice = new Label(String.format("Prix: $%.2f", cartItem.getProduct().getPrix()));
                 ////////
                 // Créer le bouton pour augmenter la quantité
                 Button increaseButton = new Button("+");
                 increaseButton.setOnAction(event -> {
                     cartItem.increaseQuantity();
                     productQuantity.setText("Quantité: " + cartItem.getQuantity());
                     productPrice.setText(String.format("Prix: $%.2f", cartItem.getTotalPrice()));
                     // Mettez à jour les données du panier, si nécessaire
                 });

                 // Créer le bouton pour diminuer la quantité
                 Button decreaseButton = new Button("-");
                 decreaseButton.setOnAction(event -> {
                     cartItem.decreaseQuantity();
                     productQuantity.setText("Quantité: " + cartItem.getQuantity());
                     productPrice.setText(String.format("Prix: $%.2f", cartItem.getTotalPrice()));
                     // Mettez à jour les données du panier, si nécessaire
                     if (cartItem.getQuantity() <= 0) {
                         cartPane.getChildren().remove(hBox); // Supprimez le HBox du cartPane si la quantité est 0
                         CartServices.getInstance().removeProduct(cartItem.getProduct().getNom()); // Supprimez le produit du service de panier
                     }
                 });
                 ///////

                 // Ajoutez tous les éléments au HBox
                 hBox.getChildren().addAll(imageView, productName, productQuantity, productPrice);

                 // Ajoutez le HBox au cartPane
                 cartPane.getChildren().add(hBox);

             }

         }

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


}