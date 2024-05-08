package tn.esprit.Controllers;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Produit;
import tn.esprit.services.PartenaireServices;
import tn.esprit.services.ProduitServices;
import tn.esprit.Controllers.AjouterProduit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

   /* public void showAvailableProducts() {

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
    }*/
   public void showAvailableProducts() {
       try {
           List<Produit> produits = pr.getAll();
           ObservableList<Produit> observableList = FXCollections.observableList(produits);
           ProdtableView.setItems(observableList);
           NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom")); // Assurez-vous que 'nom' est le nom de la propriété dans Produit
           PrixColumn.setCellValueFactory(new PropertyValueFactory<>("prix")); // Assurez-vous que 'prix' est le nom de la propriété dans Produit
           // QColumn a été supprimé car la quantité n'est plus dans Produit
           DesColumn.setCellValueFactory(new PropertyValueFactory<>("description")); // Assurez-vous que 'description' est le nom de la propriété dans Produit

           // Si vous avez un attribut partenaire_id, vous pouvez ajouter une colonne pour cela aussi

       } catch (RuntimeException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
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

    if ((num - 1) < -1 || selectedProduct == null) {
        // Handle the case where no product is selected or available.
        ProductImage.setImage(new Image("src/main/resources/images/logo.jpg")); // Fournissez un chemin par défaut.
        return;
    }

    ProductNameLabel.setText(selectedProduct.getNom());

    String imagePath = selectedProduct.getImagePath(); // getImagePath() est à remplacer par le getter approprié de l'attribut image.
    if (imagePath != null && !imagePath.isEmpty()) {
        // Supposons que imagePath est un chemin relatif dans votre projet ou une URL complète
        Image image = new Image(imagePath);
        ProductImage.setImage(image);
    } else {
        // Mettre une image par défaut si aucune image n'est trouvée
        ProductImage.setImage(new Image("src/main/resources/images/logo.jpg")); // Fournissez un chemin par défaut.
    }
}



    @FXML
    private void handleDeletePartenaire() {
        Produit selectedProduit = ProdtableView.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            // Code pour supprimer le partenaire de la base de données
            pr.delete(selectedProduit);
            // Actualiser la TableView
            ProdtableView.getItems().remove(selectedProduit);
        } else {
            // Afficher un message d'erreur si aucun partenaire n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un partenaire à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    public void onButtonClickAdd(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPartenaire.fxml"));
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

    public void onButtonClickAddProduct(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
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
    public void onButtonClickAffichePartner(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
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


    @FXML
    void handleUpdateProduitAction(ActionEvent event) {
        Produit selectedProduit = ProdtableView.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            openUpdateProduitWindow(selectedProduit);
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Aucun produit sélectionné", "Veuillez sélectionner un produit à mettre à jour.");
        }
    }



    private void openUpdateProduitWindow(Produit selectedProduit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduit.fxml"));
            Parent root = loader.load();

            UpdateProduit controller = loader.getController();
            controller.initData(selectedProduit);

            Stage stage = new Stage();
            stage.setTitle("Mise à jour du produit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }





    public void onStat(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
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


@FXML
private void openAjouterProduitWindow() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
        Parent root = loader.load();

        AjouterProduit controller = loader.getController();
        //controller.initData(selectedPartenaire);

        Stage stage = new Stage();
        stage.setTitle("Ajouter Partenaire");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}








