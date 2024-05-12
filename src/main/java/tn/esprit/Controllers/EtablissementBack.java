package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class EtablissementBack {
    @FXML
    private TableView<Etablissement> etablissementtable;


    @FXML
    private TableColumn<Etablissement, String> localisationcolomn;

    @FXML
    private TableColumn<Etablissement, String> nomcolumn;

    @FXML
    private TableColumn<Etablissement, String> typecolomn;
    private final EtablissementServices etablissementServices;

    public  EtablissementBack() {
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
    }

    @FXML
    void initialize() {
        try {
            List<Etablissement> etablissements = etablissementServices.getAll();
            ObservableList<Etablissement> observableList = FXCollections.observableList(etablissements);

            etablissementtable.setItems(observableList);
            nomcolumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            typecolomn.setCellValueFactory(new PropertyValueFactory<>("type"));
            localisationcolomn.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            observableList.forEach(etablissement -> {
                Button modifierButton = new Button("Modifier");
                int id =etablissement.getId();
                modifierButton.setUserData(id);
                modifierButton.setOnAction(event -> modifierEtablissement(etablissement));


                // Associer l'établissement au bouton "Modifier"
                modifierButton.setUserData(etablissement);

                Button supprimerButton = new Button("Supprimer");
                supprimerButton.setOnAction(event -> etablissementServices.delete(etablissement));

                etablissement.setBtnModifier(modifierButton);
                etablissement.setBtnSupprimer(supprimerButton);

            });

            etablissementtable.setItems(observableList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des établissements.");
            alert.showAndWait();
        }
    }

    @FXML
    void modifierEtablissement(Etablissement etablissement) {
        try {
            // Utilisez l'ID pour récupérer l'établissement à modifier depuis la base de données
            Etablissement etablissementAModifier = etablissementServices.getOne(etablissement.getId());

            // Ouvrir la fenêtre de modification en passant l'établissement à modifier
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEtablissement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de modification
            ModifierEtablissement controller = loader.getController();

            // Passer l'établissement à modifier au contrôleur de la fenêtre de modification
            controller.initData(etablissementAModifier);

            // Afficher la fenêtre de modification
            Stage window = (Stage) etablissementtable.getScene().getWindow();
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

    @FXML
    void naviguer(ActionEvent event) {
        try {
            // Load the FXML for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEtablissement.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the new window
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Établissement");
            stage.setScene(new Scene(root));

            // Optionally set the modality and other properties
            stage.initModality(Modality.WINDOW_MODAL);  // Set the window as modal if desired
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());  // Set the owner of the new window

            // Show the new stage, don't hide the old one
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }



}
