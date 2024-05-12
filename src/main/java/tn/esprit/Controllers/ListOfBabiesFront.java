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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.services.BabyServices;
import tn.esprit.util.MaConnexion;
import javafx.stage.FileChooser;
import java.io.File;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ListOfBabiesFront {

    @FXML
    private ListView<Baby> babyListView;
    private final BabyServices babyServices;
    @FXML
    private ListView<InfoMedicaux> infoMedicauxListView;
    public ListOfBabiesFront() {
        Connection connection = MaConnexion.getInstance().getCnx();
        babyServices = new BabyServices(connection);
    }

    @FXML
    void initialize() {
        try {
            List<Baby> babies = babyServices.getAll();
            ObservableList<Baby> observableList = FXCollections.observableList(babies);
            babyListView.setItems(observableList);
            babyListView.setCellFactory(param -> new ListCell<Baby>() {
                private final VBox vbox = new VBox();
                /* private final Button generatePdfButton = new Button("List InfoMedicaux");

                {
                   generatePdfButton.setOnAction(event -> {
                        Baby baby = getItem();
                        if (baby != null) {
                            generatePdfForSelectedInfoMedicaux();
                        }
                    });
                }*/

                @Override
                protected void updateItem(Baby item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        vbox.getChildren().clear();
                        vbox.getChildren().addAll(
                                new Label("Nom: " + item.getNom()),
                                new Label("Prénom: " + item.getPrenom()),
                                new Label("Sexe: " + item.getSexe()),
                                new Label("Taille: " + item.getTaille() + " cm"),
                                new Label("Poids: " + item.getPoids() + " kg"),
                                new Label("Date de naissance: " + item.getDateNaissance())
                               // generatePdfButton
                        );
                        setGraphic(vbox);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des bébés.");
            alert.showAndWait();
        }
    }

   /* public void generatePdfForSelectedInfoMedicaux() {
        InfoMedicaux selectedInfoMedicaux = infoMedicauxListView.getSelectionModel().getSelectedItem();
        if (selectedInfoMedicaux != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("InfoMedicauxDetails_" + selectedInfoMedicaux.getId() + ".pdf");

            File file = fileChooser.showSaveDialog(babyListView.getScene().getWindow()); // Make sure this refers to an active window

            if (file != null) {
                pdfService.createPdf(file, selectedInfoMedicaux); // Assuming createPdf takes an InfoMedicaux and File as parameters

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Generated");
                alert.setHeaderText(null);
                alert.setContentText("PDF has been successfully saved to: " + file.getPath());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select medical information from the list.");
            alert.showAndWait();
        }
    }*/
   @FXML
   void naviguer(ActionEvent event) {
       try {
           // Charger le FXML pour la nouvelle vue
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBaby.fxml"));
           Parent root = loader.load();

           // Créer une nouvelle fenêtre (Stage)
           Stage newWindow = new Stage();
           newWindow.setTitle("Ajouter un bébé"); // Titre de la nouvelle fenêtre
           newWindow.setScene(new Scene(root)); // Définir la scène de la nouvelle fenêtre

           // Afficher la nouvelle fenêtre et garder la fenêtre actuelle ouverte
           newWindow.show();
       } catch (IOException e) {
           e.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Erreur");
           alert.setContentText("Une erreur s'est produite lors du chargement de la vue AjouterBaby.");
           alert.showAndWait();
       }
   }



}