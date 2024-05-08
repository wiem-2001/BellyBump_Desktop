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
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.services.BabyServices;
import tn.esprit.services.InfoMedicauxServices;

import java.sql.Date;
import java.time.LocalDate;

public class ModifierInfoMedicaux {

    @FXML
    private ChoiceBox<String> bloodtype;
    private String[] bloodtypes = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};


    @FXML
    private DatePicker dateoflastvaccin;

    @FXML
    private Button delete;

    @FXML
    private TextArea description;

    @FXML
    private TextField diseasename;

    @FXML
    private TextField numberofvaccines;

    @FXML
    private ChoiceBox<String> sicknessestimation;
    private String[] sicknessestimations = {"25%", "50%", "75%", "100%"};


    @FXML
    private Button update;

    private InfoMedicaux infoMedicaux;

    @FXML
    void deleteinfoMedicaux(ActionEvent event) {

    }

    @FXML
    void initialize(ActionEvent event) {
        bloodtype.getItems().addAll(bloodtypes);
        sicknessestimation.getItems().addAll(sicknessestimations);
    }

    @FXML
    void naviguerEtab(ActionEvent event) {

    }

    @FXML
    void naviguerrendez(ActionEvent event) {

    }

    @FXML
    void updateInfoMedicaux(ActionEvent event) {
        try {
            InfoMedicauxServices infoMedicauxServices = new InfoMedicauxServices();

            // Récupérer les nouvelles valeurs saisies
            String nouveaumaladie = diseasename.getText();
            String nouveaudescription = description.getText();
            String nouvellebloodtype = bloodtype.getValue();
            LocalDate nouveaudatevaccin = dateoflastvaccin.getValue();
            String nouveausicknessestimation = sicknessestimation.getValue();
            String nouvellenbrvaccin = numberofvaccines.getText();

            // Vérifier que infoMedicaux n'est pas nul
            if (infoMedicaux != null) {
                infoMedicaux.setMaladie(nouveaumaladie);
                infoMedicaux.setDescription(nouveaudescription);
                infoMedicaux.setBlood_type(nouvellebloodtype);
                infoMedicaux.setDate_vaccin(Date.valueOf(nouveaudatevaccin));
                infoMedicaux.setSickness_estimation(nouveausicknessestimation);
                infoMedicaux.setNbr_vaccin(Integer.parseInt(nouvellenbrvaccin));

                // Appeler la méthode update() pour mettre à jour l'établissement dans la base de données
                infoMedicauxServices.update(infoMedicaux);

                // Fermer la fenêtre de modification après la mise à jour réussie
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Recharger la vue des babies après la modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherInfoMedicauxBack.fxml"));
                Parent root = loader.load();
                AfficherInfoMedicauxBack listController = loader.getController();
                listController.initialize();

                Scene scene = new Scene(root);
                Stage listStage = new Stage();
                listStage.setScene(scene);
                listStage.setTitle("Liste des babies");
                listStage.show();
            } else {
                // Gérer le cas où infoMedicaux est nul
                System.err.println("L'objet infoMedicaux est nul.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur : L'objet infoMedicaux est nul.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Gérer les exceptions, par exemple en affichant une alerte
            System.err.println("Erreur lors de la modification de baby : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la modification de baby : " + e.getMessage());
            alert.showAndWait();
        }
    }







    public void initData(InfoMedicaux infoMedicaux) {
        this.infoMedicaux=infoMedicaux;
        diseasename.setText(infoMedicaux.getMaladie());
        description.setText(infoMedicaux.getDescription());
        numberofvaccines.setText(String.valueOf(infoMedicaux.getNbr_vaccin())); // Convert int to String
        sicknessestimation.setValue(infoMedicaux.getSickness_estimation());
        dateoflastvaccin.setValue(infoMedicaux.getDate_vaccin().toLocalDate()); // Convert Date to LocalDate
        bloodtype.setValue(infoMedicaux.getBlood_type());

        // You also need to set the ID of the infoMedicaux

    }


}
