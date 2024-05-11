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
import javafx.stage.Stage;
import tn.esprit.entities.Medcin;
import tn.esprit.services.MedcinServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class MedcinBack {

    @FXML
    private TableView<Medcin> medcintable;
    @FXML
    private TableColumn<Medcin, String> etablissementColumn;
    @FXML
    private TableColumn<Medcin, String> nomcolumn;

    @FXML
    private TableColumn<Medcin, String> prenomcolomn;

    @FXML
    private TableColumn<Medcin, Medcin> specialitecolomn;

//    @FXML
//    private TableColumn<Medcin, Void> modifierColumn;



    private final MedcinServices medcinServices;
    public MedcinBack() {
        Connection connection = MaConnexion.getInstance().getCnx();
        medcinServices = new MedcinServices(connection);
    }

    @FXML
    void initialize() {
        try {
            List<Medcin> medcins = medcinServices.getAll();
            ObservableList<Medcin> observableList = FXCollections.observableList(medcins);

            medcintable.setItems(observableList);
            nomcolumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomcolomn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            specialitecolomn.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            etablissementColumn.setCellValueFactory(new PropertyValueFactory<>("etab_nom"));
            observableList.forEach(medcin -> {
                Button modifierButton = new Button("Modifier");
                int id =medcin.getId();
                modifierButton.setUserData(id);
                modifierButton.setOnAction(event -> modifierMedcin(medcin));

                modifierButton.setUserData(medcin);

                Button supprimerButton = new Button("Supprimer");
                supprimerButton.setOnAction(event -> medcinServices.delete(medcin));

                medcin.setBtnModifier(modifierButton);
                medcin.setBtnSupprimer(supprimerButton);

            });
            medcintable.setItems(observableList);


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des médecins.");
            alert.showAndWait();
        }
    }

    private void modifierMedcin(Medcin medcin) {
        try {
            // Charger la vue de modification du médecin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMedcin.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la vue de modification du médecin
            ModifierMedcin controller = loader.getController();

            // Passer le médecin sélectionné au contrôleur de la vue de modification
            controller.initData(medcin);

            // Afficher la fenêtre de modification du médecin
            Stage window = (Stage) medcintable.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue de modification du médecin.");
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
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterMedcin.fxml"));
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
