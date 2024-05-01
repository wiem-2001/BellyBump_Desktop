package tn.esprit.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Produit;
import tn.esprit.services.PartenaireServices;
import tn.esprit.services.ProduitServices;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Stat {

    @FXML
    private PieChart partnerProductChart;
    @FXML
    private BarChart<String, Number> productPartnerCountChart;
    @FXML
    private BarChart<String, Number> productPricingChart;
    @FXML
    private PieChart difficultToSellProductsChart;
    @FXML
    private VBox clocksContainer;

    @FXML
    public void initialize() {
        loadData();
        setupWorldClocks();
    }

    private void loadData() {
        try {
            loadPartnerProductData();
            loadProductPartnerCountData();
            loadProductPricingData();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadPartnerProductData() {


        PartenaireServices ps = new PartenaireServices();
        List<Partenaire> partenaires = ps.getAll();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Partenaire partenaire : partenaires) {
            pieChartData.add(new PieChart.Data(partenaire.getNom(), partenaire.getProduits().size()));
        }

        partnerProductChart.setData(pieChartData);
    }

    private void loadProductPartnerCountData() {
        PartenaireServices ps = new PartenaireServices();
        List<Partenaire> allPartenaires = ps.getAll();
        ProduitServices pds = new ProduitServices();
        List<Produit> allProduits = pds.getAll();

        ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
        XYChart.Series<String, Number> partnerSeries = new XYChart.Series<>();
        partnerSeries.setName("Partenaires");
        partnerSeries.getData().add(new XYChart.Data<>("Total", allPartenaires.size()));

        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.setName("Produits");
        productSeries.getData().add(new XYChart.Data<>("Total", allProduits.size()));

        series.addAll(partnerSeries, productSeries);
        productPartnerCountChart.setData(series);
    }

    private void loadProductPricingData() {
        ProduitServices pds = new ProduitServices();
        List<Produit> allProduits = pds.getAll();

        int difficultToSell = 0, easyToSell = 0;
        for (Produit produit : allProduits) {
            if (produit.getPrix() > 50) difficultToSell++;
            else easyToSell++;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Répartition des produits par prix");
        series.getData().add(new XYChart.Data<>("Difficile à vendre (>50$)", difficultToSell));
        series.getData().add(new XYChart.Data<>("Facile à vendre (<50$)", easyToSell));

        productPricingChart.getData().clear();
        productPricingChart.getData().add(series);
    }

    private void showError(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Loading Data");
        alert.setHeaderText("Could not load data");
        alert.setContentText(content);
        alert.showAndWait();
    }



    private void setupWorldClocks() {


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        String[] zones = {"America/New_York", "Europe/London", "Asia/Tokyo"};

        for (String zone : zones) {
            Label label = new Label();
            label.getStyleClass().add("clock-label");
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                label.setText(ZoneId.of(zone).getId() + " " + timeFormatter.format(java.time.ZonedDateTime.now(ZoneId.of(zone))));
            }), new KeyFrame(Duration.seconds(1)));
            clock.setCycleCount(Timeline.INDEFINITE);
            clock.play();
            clocksContainer.getChildren().add(label);
        }
    }



    public void onButtonClickAfficheProd(ActionEvent event) {
        try {
            // Charge le fichier FXML pour la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashbord.fxml"));
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




    public void onButtonClickAddPartner(ActionEvent event) {
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

}
