package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;

public class AjouterEtablissement {

    @FXML
    private TextField localisation;
    @FXML
    private ImageView backtoetablissement;
    @FXML
    private TextField nom;

    @FXML
    private ChoiceBox<String> type;

    private String [] types={"public", "privé"};
    @FXML
    public void initialize() {
        // Initialize the ChoiceBox type with the values "public" and "privé"
        type.getItems().addAll(types);
    }

    @FXML
    void ajouterEtablissement(ActionEvent event) {
        try {
            String nomEtablissement = nom.getText();
            String localisationEtablissement = localisation.getText();
            String typeEtablissement = type.getValue(); // Obtenez la valeur sélectionnée dans ChoiceBox

            // Vérifiez si tous les champs sont remplis
            if (nomEtablissement.isEmpty() || localisationEtablissement.isEmpty() || typeEtablissement == null) {
                // Affichez un message d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return; // Sortez de la méthode
            }

            // Créez une instance de votre service EtablissementServices et ajoutez l'établissement
            EtablissementServices etablissementServices = new EtablissementServices();
            etablissementServices.add(new Etablissement(nomEtablissement, typeEtablissement, localisationEtablissement));

            // Affichez un message de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Établissement ajouté avec succès !");
            successAlert.showAndWait();

//            // Fermez la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

            // Ouvrez une nouvelle fenêtre ou affichez une autre vue si nécessaire
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtablissementBack.fxml"));
            Parent root = loader.load();
            EtablissementBack listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des établissements");
            listStage.show();
        } catch (Exception e) {
            // Gérez les exceptions si nécessaire
            e.printStackTrace();
        }
    }



    @FXML
    void naviguerEtab(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EtablissementBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void naviguermed(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MedcinBack.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            // Gérer l'erreur de chargement de la vue
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    @FXML
    void naviguerrendez(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RendezVousBack.fxml"));

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            // Gérer l'erreur de chargement de la vue
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }


    }
    public void backToEtab(MouseEvent mouseEvent) {
        // Récupère la fenêtre (stage) dans laquelle le ImageView est affiché et la ferme
        Stage stage = (Stage) backtoetablissement.getScene().getWindow();
        stage.close();
    }

}
