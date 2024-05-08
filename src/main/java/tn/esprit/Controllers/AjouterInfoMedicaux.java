package tn.esprit.Controllers;

import com.ibm.icu.impl.Normalizer2Impl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import tn.esprit.entities.InfoMedicaux;

import tn.esprit.services.BabyServices;
import tn.esprit.services.InfoMedicauxServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjouterInfoMedicaux {

    @FXML
    private ChoiceBox<String> bloodtype;
    private String[] bloodtypes = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};



    @FXML
    private TextField idbaby;

    @FXML
    private DatePicker dateoflastvaccin;

    @FXML
    private TextArea description;

    @FXML
    private TextField diseasename;

    @FXML
    private TextField numberofvaccines;
private BabyServices babyServices;
    @FXML
    private ChoiceBox<String> sicknessestimation;
    private String[] sicknessestimations = {"25%", "50%", "75%", "100%"};
    private int babyId;
    @FXML
    private ComboBox<String> babyComboBox;
    private String desc;
    private String maladie;
    private int nbrVaccin;
    private LocalDate dateVaccin;
    private String bloodType;
    private String sicknessEstimation;
    private String selectedBabyName;
    private Normalizer2Impl.ReorderingBuffer nbrVaccinesText;

    // Méthode pour initialiser babyId
   /* public void initBabyId(int babyId) {
        this.babyId = babyId;
    }*/



    @FXML
    public void initialize() {
        // Initialize the ChoiceBox type with the values "O+", "O-", etc.
        bloodtype.getItems().addAll(bloodtypes);

        // Initialize the ChoiceBox for sickness estimation
        sicknessestimation.getItems().addAll(sicknessestimations);

        Connection connection = MaConnexion.getInstance().getCnx();
        babyServices = new BabyServices(connection);

        List<String> babyNames = babyServices.getAllBabyName();

        babyComboBox.getItems().addAll(babyNames);
    }

    @FXML
    void naviguerHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Statistics.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality to APPLICATION_MODAL
            stage.setScene(new Scene(root));
            stage.setTitle("Statistics"); // Set the title of the new window
            stage.showAndWait(); // Show the window and wait for it to be closed

            // Code below will only execute after the popup window is closed
            // You can add any additional logic here

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ajouterInfoMedicaux(ActionEvent event) {
        try {
            // Retrieve data from UI components
            maladie = diseasename.getText().trim();
            desc = description.getText().trim();
            nbrVaccin = Integer.parseInt(numberofvaccines.getText().trim());
            dateVaccin = dateoflastvaccin.getValue();
            bloodType = bloodtype.getValue();
            sicknessEstimation = sicknessestimation.getValue();

            // Data validation
            if (maladie.isEmpty() || desc.isEmpty() || nbrVaccin == 0 || dateVaccin == null || bloodType == null || sicknessEstimation == null) {
                // Show error message if any field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return;
            }

            // Retrieve baby ID by name
            String selectedBabyName = babyComboBox.getValue();
            int babyId = babyServices.getBabyIdByName(selectedBabyName);

            // Create InfoMedicaux object with the retrieved data
            InfoMedicaux infoMedicaux = new InfoMedicaux(babyId, maladie, desc, nbrVaccin, Date.valueOf(dateVaccin), bloodType, sicknessEstimation);

            // Add the InfoMedicaux object to the database
            InfoMedicauxServices infoMedicauxService = new InfoMedicauxServices();
            infoMedicauxService.add(infoMedicaux);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Informations médicales ajoutées avec succès !");
            successAlert.showAndWait();

            // Close the current window if necessary
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Handle number format exception for parsing number of vaccines
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez entrer un nombre valide pour le nombre de vaccins !");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }
    @FXML
    void naviguerinfoMedicaux(ActionEvent event){}
    @FXML
    void naviguerBaby(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBaby.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un bébé");
            stage.show();

            // Close the current stage (if needed)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void naviguerListOfBabiesFront(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListOfBabiesFront.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("List of Babies");
            stage.show();

            // Close the current stage (if needed)
            Stage currentStage = (Stage) idbaby.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
