package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ModifierEtablissement {

    @FXML
    private TextField localisation;

    @FXML
    private TextField nom;

    @FXML
    private ChoiceBox<String> type;

    private final EtablissementServices etablissementServices;
    private Etablissement etablissement;

    private final String[] types = {"public", "privé"};

    public ModifierEtablissement() {
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
    }

    @FXML
    public void initialize() {
        // Initialize the ChoiceBox type with the values "public" and "privé"
        type.getItems().addAll(types);
    }

    public void initData(Etablissement etablissement) {
        this.etablissement = etablissement;
        nom.setText(etablissement.getNom());
        type.setValue(etablissement.getType());
        localisation.setText(etablissement.getLocalisation());
    }

    @FXML
    void modifierEtablissement(ActionEvent event) {
        try {
            // Récupérer les nouvelles valeurs saisies
            String nouveauNom = nom.getText();
            String nouveauType = type.getValue();
            String nouvelleLocalisation = localisation.getText();

            // Mettre à jour l'établissement avec les nouvelles valeurs
            etablissement.setNom(nouveauNom);
            etablissement.setType(nouveauType);
            etablissement.setLocalisation(nouvelleLocalisation);

            // Appeler la méthode update() pour mettre à jour l'établissement dans la base de données
            etablissementServices.update(etablissement);

            // Fermer la fenêtre de modification après la mise à jour réussie
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Recharger la vue des établissements après la modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtablissementBack.fxml"));
            Parent root = loader.load();
            EtablissementBack listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des établissements");
            listStage.show();
        } catch (IOException e) {
            // Gérer les exceptions liées au chargement de la vue
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }

    // Autres méthodes de navigation...


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


}
