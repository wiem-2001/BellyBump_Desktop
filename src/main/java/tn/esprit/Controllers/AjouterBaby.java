package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.services.BabyServices;

import java.io.IOException;
import java.time.LocalDate;

public class AjouterBaby {

    @FXML
    private DatePicker dateofbirth;

    @FXML
    private ChoiceBox<String> gender;
    private String [] genders={"masculin", "feminine"};
    @FXML
    public void initialize() {
        // Initialize the ChoiceBox type with the values "masculin" and "feminine"
        gender.getItems().addAll(genders);
    }

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private TextField size;

    @FXML
    private TextField weight;

    @FXML
    void ajouterBaby(ActionEvent event) {
        try {
            // Retrieve data from UI components
            String nom = name.getText();
            String prenom = lastname.getText();
            String sexe = gender.getValue();
            LocalDate dateNaissance = dateofbirth.getValue();
            float poids = Float.parseFloat(weight.getText());
            float taille = Float.parseFloat(size.getText());

            // Data validation
            // Validate if all fields are filled
            if (nom.isEmpty() || prenom.isEmpty() || sexe == null || dateNaissance == null || weight.getText().isEmpty() || size.getText().isEmpty()) {
                // Display an error message if any field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return; // Exit the method
            }

            // Create an instance of your BabyServices and add the baby
            BabyServices babyServices = new BabyServices();
            Baby newBaby = new Baby(nom, prenom, sexe, dateNaissance, poids, taille);
            babyServices.add(newBaby);

            // Display a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Bébé ajouté avec succès !");
            successAlert.showAndWait();

            // Close the current window if necessary
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // If you need to navigate to another view, use FXMLLoader and display the new view
            navigateToAjouterInfoMedicaux(event);
        } catch (Exception e) {
            // Handle exceptions if necessary
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToAjouterInfoMedicaux(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterInfoMedicaux.fxml"));
            Parent root = loader.load();

            // Obtain the controller of the AjouterInfoMedicaux view
            AjouterInfoMedicaux controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter des informations médicales");
            stage.show();

            // Close the current window if necessary
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            // Handle exceptions if necessary
            e.printStackTrace();
        }
    }







    @FXML
    void initialize(ActionEvent event) {

    }

   

    public void naviguerInfoMedicaux(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterInfoMedicaux.fxml"));
            Parent root = loader.load();

            // Obtain the controller of the AjouterInfoMedicaux view
            AjouterInfoMedicaux controller = loader.getController();

            // Pass the baby ID to the AjouterInfoMedicaux controller
            // controller.initBabyId(babyId); // Assurez-vous que babyId est défini correctement dans votre classe AjouterBaby

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter des informations médicales");
            stage.show();

            // Fermez la fenêtre actuelle si nécessaire
          //  Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //currentStage.close();
        } catch (IOException e) {
            // Gérer les exceptions si nécessaire
            e.printStackTrace();
        }
    }

    public void naviguerBaby(ActionEvent actionEvent) {
    }
}
