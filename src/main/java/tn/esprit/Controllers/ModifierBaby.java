package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.services.BabyServices;

import java.time.LocalDate;

public class ModifierBaby {

    @FXML
    private DatePicker dateofbirth;

    @FXML
    private Button delete;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private TextField lastname;

    @FXML
    private TextField name;

    @FXML
    private TextField size;

    @FXML
    private Button update;

    @FXML
    private TextField weight;
    private String [] genders={"masculin", "feminine"};
    private Baby baby;

    @FXML
    public void initialize() {
        // Initialize the ChoiceBox type with the values "masculin" and "feminine"
        gender.getItems().addAll(genders);
    }

    @FXML
    void modifierBaby(ActionEvent event) {

        try {
            BabyServices babyServices = new BabyServices();

            // Récupérer les nouvelles valeurs saisies
            String nouveauname = name.getText();
            String nouveaulastname = lastname.getText();
            String nouvellegender = gender.getValue();
            LocalDate nouveaudateofbirth = dateofbirth.getValue();
            float poids = Float.parseFloat(weight.getText());
            float taille = Float.parseFloat(size.getText());

            // Récupérer l'établissement à modifier (peut-être à passer en argument)
            // Assurez-vous de récupérer l'ID de l'établissement également

            // Créer un nouvel objet Etablissement avec les modifications

            baby.setNom(nouveauname);
            baby.setPrenom(nouveaulastname);
            baby.setSexe(nouvellegender);
            baby.setDateNaissance(nouveaudateofbirth);
            baby.setPoids(poids);
            baby.setTaille(taille);

            // Appeler la méthode update() pour mettre à jour l'établissement dans la base de données
            babyServices.update(baby);

            // Fermer la fenêtre de modification après la mise à jour réussie
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Recharger la vue des babies après la modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBabyBack.fxml"));
            Parent root = loader.load();
           AfficherBabyBack listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des babies");
            listStage.show();
        } catch (Exception e) {
            // Gérer les exceptions, par exemple en affichant une alerte
            System.err.println("Erreur lors de la modification de baby : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de baby : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void naviguerEtab(ActionEvent event) {

    }

    @FXML
    void naviguerrendez(ActionEvent event) {

    }

    @FXML
    void supprimerBaby(ActionEvent event) {

    }

    public void initData(Baby baby) {
        this.baby=baby;
        name.setText(baby.getNom());
        lastname.setText(baby.getPrenom());
        gender.setValue(baby.getSexe());
        dateofbirth.setValue(baby.getDateNaissance());
        size.setText(String.valueOf(baby.getPoids())); // Convert float to string
        weight.setText(String.valueOf(baby.getTaille())); // Convert float to string

        // You also need to set the ID of the establishment
        // For example: this.etablissementId = etablissement.getId();
    }

}
