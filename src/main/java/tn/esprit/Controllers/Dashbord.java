package tn.esprit.Controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Produit;
import tn.esprit.services.PartenaireServices;
import tn.esprit.services.ProduitServices;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Dashbord implements Initializable {

    @FXML
    private Circle CircleImage;

    @FXML
    private TableColumn<?, ?> DesColumn;

    @FXML
    private TableColumn<?, ?> NomColumn;

    @FXML
    private TableColumn<?, ?> PrixColumn;

    @FXML
    private TableView<Produit> ProdtableView;

    @FXML
    private ImageView ProductImage;

    @FXML
    private TableColumn<?, ?> QColumn;

    @FXML
    private Button availableProductsBtn;

    @FXML
    private Button cartbtn;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private Button saveBtn;

    @FXML
    private Button takeBtn;

    @FXML
    private Button arrow_btn;

    @FXML
    private Button bars_btn;
    @FXML
    private Label ProductNameLabel;


    private final ProduitServices pr = new ProduitServices();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvailableProducts();

    }

    public void showAvailableProducts() {

        try {
            List<Produit> produits = pr.getAll();
            produits = pr.getAll();
            ObservableList<Produit> observableList = FXCollections.observableList(produits);
            ProdtableView.setItems(observableList);
            NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            PrixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            QColumn.setCellValueFactory(new PropertyValueFactory<>("quantiteDisponible"));
            DesColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }


    public void sliderArrow() {

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


    public void sliderBars() {

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




// Methode pour l affichage de image de produit selectioné
   public void selectProduct() {

        Produit selectedProduct = ProdtableView.getSelectionModel().getSelectedItem();
        int num = ProdtableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        ProductNameLabel.setText(selectedProduct.getNom());




       //Image uri =  selectedProduct.getImageFX();

       //image = new Image(uri, 134, 171, false, true);

       //ProductImage.setImage(selectedProduct.getImageFX());

       if (selectedProduct == null) {
           ProductImage.setImage(new Image("src/main/resources/images/logo.jpg")); // Remplacez avec un chemin valide
           return;
       }

       try {
           Image productImage = selectedProduct.getImageFX();
           if (productImage != null) {
               ProductImage.setImage(productImage);
           } else {
               ProductImage.setImage(new Image("src/main/resources/images/logo.jpg")); // Remplacez avec un chemin valide
           }
       } catch (Exception e) {
           e.printStackTrace(); // Pour voir l'exception si elle se produit
           // Gérer l'exception ici, par exemple en affichant une alerte à l'utilisateur
       }

    }





}


