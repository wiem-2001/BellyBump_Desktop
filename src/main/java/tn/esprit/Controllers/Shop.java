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
import javafx.scene.input.MouseEvent;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;


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
    private VBox cardLayout;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox mainCenter_form;

    //private CartServices cartController;
    private CartUpdateListener cartUpdateListener;

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private TextField searchField;

    //filter///
    @FXML
    private TextField minPriceField;

    @FXML
    private TextField maxPriceField;

    private List<Produit> currentFilteredProducts;
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
                VBox cardBox = fxmlLoader.load(); // Load the FXML file first
                Card cardController = fxmlLoader.getController(); // Now you can retrieve the controller
                cardController.setData(produit);

                //cardController.setCartUpdateListener(cartController); // Assuming cartController is already initialized
                cardLayout.getChildren().add(cardBox);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        displayProducts(new ProduitServices().getAll());
        ///sort init///

        currentFilteredProducts = ps.getAll();
        displayProductsSort(currentFilteredProducts);

        ////sort iit////
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







    ///////////pour la recherche/////////

    @FXML
    private void handleSearchAction(ActionEvent event) {
        try {
            if (searchField != null && searchField.getText() != null) {
                String searchText = searchField.getText().toLowerCase();
                updateProductDisplay(searchText);
            } else {
                System.out.println("Search field is not initialized.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateProductDisplay(String searchText) {
        ProduitServices ps = new ProduitServices();
        List<Produit> filteredProducts = ps.getAll().stream()
                .filter(produit -> produit.getNom().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }

    private void displayProducts(List<Produit> products) {
        cardLayout.getChildren().clear();  // Clear existing product cards first
        for (Produit produit : products) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                Card cardController = fxmlLoader.getController();
                cardController.setData(produit);
                cardLayout.getChildren().add(cardBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /////filte/////
    @FXML
    private void handleFilterAction(ActionEvent event) {
        try {
            double minPrice = Double.parseDouble(minPriceField.getText().isEmpty() ? "0" : minPriceField.getText());
            double maxPrice = Double.parseDouble(maxPriceField.getText().isEmpty() ? Double.toString(Double.MAX_VALUE) : maxPriceField.getText());
            updateProductDisplay(searchField.getText(), minPrice, maxPrice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price input");
        }
    }


    private void updateProductDisplay(String searchText, double minPrice, double maxPrice) {
        ProduitServices ps = new ProduitServices();
        List<Produit> filteredProducts = ps.getAll().stream()
                .filter(produit -> produit.getNom().toLowerCase().contains(searchText.toLowerCase()))
                .filter(produit -> produit.getPrix() >= minPrice && produit.getPrix() <= maxPrice)
                .collect(Collectors.toList());

        displayProducts(filteredProducts);
    }

    ///////////
    //private List<Produit> currentFilteredProducts;  // Stocke les produits actuellement filtrés ou affichés

    @FXML
    private void handleSortAsc(MouseEvent event) {
        System.out.println("Ascending sort clicked");
        if (currentFilteredProducts != null) {
            List<Produit> sortedProducts = currentFilteredProducts.stream()
                    .sorted(Comparator.comparingDouble(Produit::getPrix))
                    .collect(Collectors.toList());
            displayProducts(sortedProducts);
        } else {
            System.out.println("No products to sort");
        }
    }

    @FXML
    private void handleSortDesc(MouseEvent event) {
        System.out.println("Descending sort clicked");
        if (currentFilteredProducts != null) {
            List<Produit> sortedProducts = currentFilteredProducts.stream()
                    .sorted(Comparator.comparingDouble(Produit::getPrix).reversed())
                    .collect(Collectors.toList());
            displayProductsSort(sortedProducts);
        } else {
            System.out.println("No products to sort");
        }
    }
    private void displayProductsSort(List<Produit> products) {
        cardLayout.getChildren().clear(); // Clear existing product cards first
        for (Produit produit : products) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                Card cardController = fxmlLoader.getController();
                cardController.setData(produit);
                cardLayout.getChildren().add(cardBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
