package tn.esprit.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.services.BabyServices;
import tn.esprit.util.MaConnexion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;


public class AfficherBabyBack {
    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private TableView<Baby> Babytable;

    @FXML
    private TableColumn<Baby, LocalDate> dateNaissanceColumn;

    @FXML
    private TableColumn<Baby, String> nomcolumn;

    @FXML
    private TableColumn<Baby, Float> poidsColumn;

    @FXML
    private TableColumn<Baby, String> prenomcolomn;

    @FXML
    private TableColumn<Baby, String> sexecolomn;

    @FXML
    private TableColumn<Baby, Float> tailleColumn;

    @FXML
    private TableColumn<Baby, Void> updateColumn;

    @FXML
    private TableColumn<Baby, Void> deleteColumn;

    @FXML
    private TextField recherche;

    @FXML
    private Button searchbutton;
    @FXML
    private PieChart genderPieChart;

    @FXML
    private Label sicknessPercentageLabel; // Assuming you have a Label in your FXML for displaying the percentage

    private BabyServices babyServices; // Add a reference to BabyServices

    @FXML
    void recherche(ActionEvent event) {

    }

    private Image generateQRCode(String text, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Convert BufferedImage to JavaFX Image
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void generateQRCodeForSelectedBaby(ActionEvent event) {
        Baby selectedBaby = Babytable.getSelectionModel().getSelectedItem();
        if (selectedBaby != null) {
            displayRandomWebsiteQRCode();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Baby Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a baby from the table.");
            alert.showAndWait();
        }
    }

    // Method to display QR code for a randomly selected website
    private void displayRandomWebsiteQRCode() {
        String[] websites = {
                "https://www.unicef.org/parenting/child-development/baby-tips",
                "https://kidshealth.org/en/parents/guide-parents.html",
                "https://www.michigan.gov/mikidsmatter/parents/infant/parenting",
                "https://www.nhs.uk/conditions/baby/support-and-services/tips-for-new-parents/",
                "https://www.parents.com/baby/care/newborn/your-newborn-30-tips-for-the-first-30-days/"
        };
        Random random = new Random();
        int randomIndex = random.nextInt(websites.length);
        String selectedWebsite = websites[randomIndex];
        Image qrCodeImage = generateQRCode(selectedWebsite, 200, 200);
        qrCodeImageView.setImage(qrCodeImage);
    }

    @FXML
    void naviguerBaby(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherBabyBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void naviguerInfoMedicaux(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherInfoMedicauxBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }


    public AfficherBabyBack() {
        try {
            Connection connection = MaConnexion.getInstance().getCnx();
            babyServices = new BabyServices(connection);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to initialize BabyServices.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        try {
            List<Baby> babies = babyServices.getAll();
            ObservableList<Baby> observableList = FXCollections.observableList(babies);
            Babytable.setItems(observableList);

            nomcolumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomcolomn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            sexecolomn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
            dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            tailleColumn.setCellValueFactory(new PropertyValueFactory<>("taille"));
            poidsColumn.setCellValueFactory(new PropertyValueFactory<>("poids"));

            double boysPercentage = babyServices.calculateBoysPercentage();
            double girlsPercentage = babyServices.calculateGirlsPercentage();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Boys", boysPercentage),
                    new PieChart.Data("Girls", girlsPercentage)
            );
            genderPieChart.setData(pieChartData);
            sicknessPercentageLabel.setText("Boys Percentage: " + String.format("%.2f%%", boysPercentage)
                    + " | Girls Percentage: " + String.format("%.2f%%", girlsPercentage));

            updateColumn.setCellFactory(param -> new TableCell<>() {
                private final Button modifierButton = new Button("Modifier");

                {
                    modifierButton.setOnAction(event -> {
                        Baby baby = getTableView().getItems().get(getIndex());
                        modifierbaby(baby);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(modifierButton);
                    }
                }
            });

            deleteColumn.setCellFactory(param -> new TableCell<>() {
                private final Button supprimerButton = new Button("Supprimer");

                {
                    supprimerButton.setOnAction(event -> {
                        Baby baby = getTableView().getItems().get(getIndex());
                        babyServices.delete(baby);
                        getTableView().getItems().remove(baby);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(supprimerButton);
                    }
                }
            });

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des établissements.");
            alert.showAndWait();
        }
    }

    @FXML
    void modifierbaby(Baby baby) {
        try {
            Baby babyAModifier = babyServices.getOne(baby.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierBaby.fxml"));
            Parent root = loader.load();
            ModifierBaby controller = loader.getController();
            controller.initData(babyAModifier);
            Stage window = (Stage) Babytable.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    @FXML
    void search(ActionEvent event) {
        try {
            String searchName = recherche.getText(); // Get the entered name from the text field
            if (!searchName.isEmpty()) {
                List<Baby> searchResults = babyServices.search(searchName); // Call search method with the name
                ObservableList<Baby> observableList = FXCollections.observableList(searchResults);
                Babytable.setItems(observableList);
            } else {
                // If the search field is empty, display all babies again
                List<Baby> babies = babyServices.getAll();
                ObservableList<Baby> observableList = FXCollections.observableList(babies);
                Babytable.setItems(observableList);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while searching for babies.");
            alert.showAndWait();
        }
    }
}
