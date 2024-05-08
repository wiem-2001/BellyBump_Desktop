package tn.esprit.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Baby;
import tn.esprit.services.BabyServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ListOfBabiesFront {

    @FXML
    private ListView<Baby> babyListView;

    private final BabyServices babyServices;

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
                private final Button afficherInfoMedicauxButton = new Button("Afficher les informations médicaux");
                private final ImageView imageView = new ImageView();
                private final VBox vbox = new VBox();

                {
                    afficherInfoMedicauxButton.setOnAction(event -> {
                        Baby baby = getItem();
                        if (baby != null) {
                            naviguerVersListOfInfoMedicaux(baby);
                        }
                    });
                }

                @Override
                protected void updateItem(Baby item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {


                        vbox.getChildren().clear();
                        vbox.getChildren().addAll(imageView,
                                new Label("Nom: " + item.getNom()),
                                new Label("Prénom: " + item.getPrenom()),
                                new Label("Sexe: " + item.getSexe()),
                                new Label("Taille: " + item.getTaille()),
                                new Label("Poids: " + item.getPoids()),
                                new Label("Date de naissance: " + item.getDateNaissance()),
                                afficherInfoMedicauxButton);

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




    private void naviguerVersListOfInfoMedicaux(Baby baby) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListOfInfoMedicauxFront.fxml"));
            Parent root = loader.load();
            ListOfInfoMedicaux controller = loader.getController();
            controller.initData(baby);

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





}