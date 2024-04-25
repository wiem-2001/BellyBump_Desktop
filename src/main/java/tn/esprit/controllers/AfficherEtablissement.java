package tn.esprit.controllers;

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
import tn.esprit.entities.Etablissement;
import tn.esprit.services.EtablissementServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AfficherEtablissement {

    @FXML
    private TableColumn<Etablissement, String> TypeColumn;

    @FXML
    private TableView<Etablissement> etablissementTable;

    @FXML
    private TableColumn<Etablissement, String> localisationColumn;

    @FXML
    private TableColumn<Etablissement, String> nomColum;
    private final EtablissementServices etablissementServices;

    public AfficherEtablissement() {
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
    }

    @FXML
    void initialize() {
        try {
            List<Etablissement> etablissements = etablissementServices.getAll();
            ObservableList<Etablissement> observableList = FXCollections.observableList(etablissements);

            etablissementTable.setItems(observableList);
            nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
            TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));

            TableColumn<Etablissement, Void> actionColumn = new TableColumn<>("Action");
            actionColumn.setMinWidth(100);

            Button afficherMedcinButton = new Button("Afficher Medcin");
            afficherMedcinButton.setOnAction(event -> {
                Etablissement etablissement = etablissementTable.getSelectionModel().getSelectedItem();
                naviguerVersAfficherMedcin(etablissement);
            });

            actionColumn.setCellFactory(param -> new TableCell<Etablissement, Void>() {
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(afficherMedcinButton);
                    }
                }
            });

            etablissementTable.getColumns().add(actionColumn);
            etablissementTable.setItems(observableList);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des établissements.");
            alert.showAndWait();
        }
    }

    // Méthode pour naviguer vers l'interface AfficherMedcin
    private void naviguerVersAfficherMedcin(Etablissement etablissement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMedcin.fxml"));
            Parent root = loader.load();
            AfficherMedcin controller = loader.getController();
            controller.initData(etablissement); // Passer l'établissement sélectionné au contrôleur AfficherMedcin

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
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
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherMedcin.fxml"));
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

    public void naviguermed(ActionEvent actionEvent) {
    }

    public void naviguerrendez(ActionEvent actionEvent) {
    }

    public void naviguerEtab(ActionEvent actionEvent) {
    }
}
