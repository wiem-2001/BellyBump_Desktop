package tn.esprit.Controllers;

import javafx.scene.chart.PieChart;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.services.BabyServices;
import tn.esprit.services.InfoMedicauxServices;
import tn.esprit.util.MaConnexion;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AfficherInfoMedicauxBack {
    @FXML
    private PieChart genderPieChart;

    @FXML
    private Label sicknessPercentageLabel;


    @FXML
    private TableView<InfoMedicaux> InfoMedicauxtable;

    @FXML
    private Button babylisteButton;

    @FXML
    private TableColumn<InfoMedicaux, String> bloodtypeColumn;

    @FXML
    private TableColumn<InfoMedicaux, LocalDate> datevaccinColumn;



    @FXML
    private TableColumn<InfoMedicaux, String> descriptioncolomn;

    @FXML
    private Button infoMedicauxButton;

    @FXML
    private TableColumn<InfoMedicaux, String> maladiecolomn;

    @FXML
    private TableColumn<InfoMedicaux, String> nombabycolumn;

    @FXML
    private TableColumn<InfoMedicaux, Integer> nombrevaccinColumn;

    @FXML
    private TableColumn<InfoMedicaux, String> sicknessestimationColumn;

    @FXML
    private TableColumn<InfoMedicaux, Void> updateColumn;

    @FXML
    private TableColumn<InfoMedicaux, Void> deleteColumn;

    @FXML
    void naviguerBaby(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherBabyBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while loading the view.");
            alert.showAndWait();
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

    public  AfficherInfoMedicauxBack() {
        Connection connection = MaConnexion.getInstance().getCnx();

    }
    @FXML
    void initialize() {
        try {
            InfoMedicauxServices infoMedicauxServices = new InfoMedicauxServices(MaConnexion.getInstance().getCnx());
            List<InfoMedicaux> infoMedicauxs = infoMedicauxServices.getAll();
            ObservableList<InfoMedicaux> observableList = FXCollections.observableList(infoMedicauxs);
            InfoMedicauxtable.setItems(observableList);

            nombabycolumn.setCellValueFactory(new PropertyValueFactory<>("babynom"));
            maladiecolomn.setCellValueFactory(new PropertyValueFactory<>("maladie"));
            descriptioncolomn.setCellValueFactory(new PropertyValueFactory<>("description"));
            nombrevaccinColumn.setCellValueFactory(new PropertyValueFactory<>("nbr_vaccin"));
            datevaccinColumn.setCellValueFactory(new PropertyValueFactory<>("date_vaccin"));
            bloodtypeColumn.setCellValueFactory(new PropertyValueFactory<>("blood_type"));
            sicknessestimationColumn.setCellValueFactory(new PropertyValueFactory<>("sickness_estimation"));

            genderPieChart.setStyle("-fx-background-color: red;");

            // Set color to red for sicknessPercentageLabel
            sicknessPercentageLabel.setStyle("-fx-text-fill: red;");
            // Calculate and set the initial percentage
            double initialPercentage = calculateSicknessPercentage(infoMedicauxs);
            setPieChartData(initialPercentage);

            updateColumn.setCellFactory(param -> new TableCell<>() {
                private final Button modifierButton = new Button("Modifier");

                {
                    modifierButton.setOnAction(event -> {
                        InfoMedicaux infoMedicaux = getTableView().getItems().get(getIndex());
                        modifierinfoMedicaux(infoMedicaux);
                        // Recalculate and update the percentage when a record is modified
                        double newPercentage = calculateSicknessPercentage(infoMedicauxServices.getAll());
                        setPieChartData(newPercentage);
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
                        InfoMedicaux infoMedicaux = getTableView().getItems().get(getIndex());
                        infoMedicauxServices.delete(infoMedicaux);
                        observableList.remove(infoMedicaux);
                        // Recalculate and update the percentage when a record is deleted
                        double newPercentage = calculateSicknessPercentage(infoMedicauxServices.getAll());
                        setPieChartData(newPercentage);
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
            alert.setContentText("Une erreur s'est produite lors du chargement des infoMedicaux.");
            alert.showAndWait();
        }
    }

    // Method to calculate the sickness percentage
    private double calculateSicknessPercentage(List<InfoMedicaux> infoMedicauxList) {
        int totalRecords = infoMedicauxList.size();
        int sickRecords = (int) infoMedicauxList.stream().filter(infoMedicaux -> !infoMedicaux.getSickness_estimation().equals("Healthy")).count();
        return (double) sickRecords / totalRecords * 100;
    }

    // Method to update the pie chart data
    private void setPieChartData(double percentage) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Healthy", 100 - percentage),
                new PieChart.Data("Sick", percentage)
        );
        genderPieChart.setData(pieChartData);
        sicknessPercentageLabel.setText("Sickness Percentage: " + String.format("%.2f%%", percentage));
    }




    @FXML
    void modifierinfoMedicaux(InfoMedicaux infoMedicaux) {
        try {
            InfoMedicauxServices infoMedicauxServices = new InfoMedicauxServices(MaConnexion.getInstance().getCnx());
            InfoMedicaux infoMedicauxAModifier = infoMedicauxServices.getOne(infoMedicaux.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierinfoMedicaux.fxml"));
            Parent root = loader.load();
            ModifierInfoMedicaux controller = loader.getController();
            controller.initData(infoMedicauxAModifier);
            Stage window = (Stage) InfoMedicauxtable.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    public void naviguer(ActionEvent actionEvent) {
    }
}

