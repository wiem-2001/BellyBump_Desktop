package tn.esprit.Controllers;

import com.google.zxing.WriterException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import tn.esprit.services.ProduitServices;
import tn.esprit.services.QRCodeGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;


public class Shop implements Initializable {

    @FXML
    private Button arrow_btn;

    @FXML
    private Button availableProductsBtn;

    @FXML
    private Button bars_btn;

    @FXML
    private Button cartbtn;

    @FXML
    private AnchorPane nav_form;
    @FXML
    private HBox cardLayout;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox mainCenter_form;

    //private CartServices cartController;
    private CartUpdateListener cartUpdateListener;

    @FXML
    private ImageView qrCodeImageView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //////////partiQRCODE///////////////
        try {
            String qrCodeUrl = "http://127.0.0.1:8000/partner"; // Replace with the URL you want to encode
            BufferedImage bufferedImage = QRCodeGenerator.generateQRCodeImage(qrCodeUrl);
            Image qrCodeImage = SwingFXUtils.toFXImage(bufferedImage, null);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException e) {
            e.printStackTrace();
            // Handle exception here
        }

        qrCodeImageView.setOnMouseClicked(event -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI("http://127.0.0.1:8000/partner")); // Replace with your URL
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ///////end QRCODE/////////
        //cartUpdateListener = (CartUpdateListener) cartController;
        //cartController = CartServices.getInstance();
        cardLayout.setAlignment(Pos.CENTER);
        ProduitServices ps = new ProduitServices();
        List<Produit> listProduct = ps.getAll();

        try {
            //List<Produit> listProduct = ps.getAll();
            for (Produit produit : listProduct) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL cardFXMLURL = getClass().getResource("/Card.fxml");
                if (cardFXMLURL == null) {
                    throw new IllegalStateException("Cannot find Card.fxml. Make sure the FXML file exists and is placed correctly.");
                }
                fxmlLoader.setLocation(cardFXMLURL);
                HBox cardBox = fxmlLoader.load(); // Load the FXML file first
                Card cardController = fxmlLoader.getController(); // Now you can retrieve the controller
                cardController.setData(produit);

                //cardController.setCartUpdateListener(cartController); // Assuming cartController is already initialized
                cardLayout.getChildren().add(cardBox);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sliderArrow() {
        TranslateTransition slide = new TranslateTransition();

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
        slide.play();

    }


    @FXML
    void sliderBars() {
        TranslateTransition slide = new TranslateTransition();

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
        slide.play();

    }


    @FXML
    public void onButtonClickAfficher(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cart.fxml"));
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


   /* private Cart cartController;
    public void setCartController(Cart cartController) {
        this.cartController = cartController;
    }
    @FXML
    void ToCartButon(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cart.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
*/
  /*  private void loadCards() {
        ProduitServices ps = new ProduitServices();
        List<Produit> listProduct = ps.getAll(); // This method should return a list of products

        try {
            for (int i = 0; i < listProduct.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                Card card = fxmlLoader.getController();
                card.setData(listProduct.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
