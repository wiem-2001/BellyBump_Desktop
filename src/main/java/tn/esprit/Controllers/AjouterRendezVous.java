package tn.esprit.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Medcin;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.EtablissementServices;
import tn.esprit.services.MedcinServices;
import tn.esprit.services.RendezVousServices;
import tn.esprit.util.MaConnexion;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

import static java.awt.List.*;

public class AjouterRendezVous {
    @FXML
    private ImageView backtomedcin;

    private Medcin selectedMedcin;
    @FXML
    private DatePicker dateReservation;
    @FXML
    private ComboBox<String> medcinComboBox;
    @FXML
    private TextField heure;
    private MedcinServices medcinServices;
    @FXML
    private TextField phoneNumberTextField;
    private final RendezVousServices ps = new RendezVousServices();
    @FXML
    private void initialize() {
        // Assuming you have a database connection available
        Connection connection = MaConnexion.getInstance().getCnx();
        medcinServices = new MedcinServices(connection);
//        Medcin selectedMedcin = ((AffichezRendezVous) FXMLLoader.getController()).getSelectedMedcin();
//        if (selectedMedcin != null) {
//            medcinComboBox.setValue(selectedMedcin.getNom()); // Initialiser le ComboBox avec le nom du médecin
//        }else{
        java.util.List<String> medcinNames = medcinServices.getAllMedcinNames();
        medcinComboBox.getItems().addAll(medcinNames);
    }
    public void initData(Medcin medcin) {
        selectedMedcin = medcin;
        // Initialiser le ComboBox avec le nom du médecin sélectionné
        if (selectedMedcin != null) {
            medcinComboBox.setValue(selectedMedcin.getNom());
        }
    }
    @FXML
    void AjouterRendezVous(ActionEvent event) {


        try {
            LocalDate date = dateReservation.getValue(); // Correction : Utilisation de dateReservation.getValue()
             int H =Integer.parseInt(heure.getText());
            String  selectedMedcin = medcinComboBox.getValue();
            String numeroTelephone = phoneNumberTextField.getText();
            RendezVous nouveauRendezVous = new RendezVous(date, H);
            RendezVousServices redezviusServices = new RendezVousServices();
            // Vérifier que l'heure est comprise entre 8 et 18 inclusivement
            if (H < 8 || H > 18) {
                // Afficher une alerte si l'heure est en dehors de la plage autorisée
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("L'heure doit être entre 8 heure et 18 heure .");
                alert.showAndWait();
                return;            }

            // Vérifier que la date est dans le futur
            if (date.isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("La date doit être dans le futur.");
                alert.showAndWait();
                return;            }
            // Vérifier si un rendez-vous existe pour cette date
            RendezVous rendezVousExist = redezviusServices.getRendezVousByDate(date);
            if (rendezVousExist != null) {
                // Si un rendez-vous existe déjà pour cette date, vérifier l'heure
                if (rendezVousExist.getHeure() == H) {
                    // Afficher une alerte si l'heure est également occupée
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Désolé, cette date et heure sont déjà occupées.");
                    alert.showAndWait();
                    return; // Retourner seulement si la date et l'heure sont déjà occupées
                }
            }





            redezviusServices.add(nouveauRendezVous,selectedMedcin);
            System.out.println("Rendez-vous ajouté avec succès !");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Rendez-vous ajouté avec succès !");
            successAlert.showAndWait();
            // Après avoir ajouté le rendez-vous avec succès

            // Vérifier si le numéro saisi est valide avant d'envoyer le SMS
            if (isValidPhoneNumber(numeroTelephone)) {
                // Envoyer le SMS avec Twilio
                SMSService.sendSMS(numeroTelephone, "Votre rendez-vous a été confirmé pour le "+date.toString()+"à "+H+" heure "+"chez "+selectedMedcin);
                // Autres actions après l'envoi du SMS
            } else {
                // Afficher une alerte si le numéro de téléphone n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un numéro de téléphone valide.");
                alert.showAndWait();
            }
//            String numeroTelephone = "+21699207977"; //  le numéro de téléphone saisi par l'utilisateur
//            String messageConfirmation = "Votre rendez-vous a été confirmé pour le "+date.toString()+"a l'heure "+H;
//            SMSService.sendSMS(numeroTelephone, messageConfirmation);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEtablissement.fxml"));
            Parent root = loader.load();
            AfficherEtablissement listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle(" Rendez-vous");
            listStage.show();
        } catch (Exception e) {
//            System.err.println("Erreur lors de l'ajout du rendez-vous : " + e.getMessage());
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Erreur lors de l'ajout du rendez-vous : " + e.getMessage());
//            alert.showAndWait();
        }
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifier si le numéro n'est pas vide et commence par "+216" et a une longueur totale de 12 caractères
        return !phoneNumber.isEmpty() && phoneNumber.startsWith("+216") && phoneNumber.length() == 12;
    }


    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEtablissement.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void backtorendezvous(MouseEvent mouseEvent) {
        Stage stage = (Stage) backtomedcin.getScene().getWindow();
        stage.close();
    }
}
