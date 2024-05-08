package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Baby;
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.services.InfoMedicauxServices;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.util.List;

public class ListOfInfoMedicaux {

    @FXML
    private ListView<InfoMedicaux> infoMedicauxListView;

    private final InfoMedicauxServices infoMedicauxServices;
    private Baby baby;

    public ListOfInfoMedicaux() {
        Connection connection = MaConnexion.getInstance().getCnx();
        infoMedicauxServices = new InfoMedicauxServices(connection);
    }

    @FXML
    void initialize() {
        chargerInfoMedicauxAssocies();
        infoMedicauxListView.setCellFactory(param -> new ListCell<InfoMedicaux>() {
            private final Label maladieLabel = new Label();
            private final Label descriptionLabel = new Label();
            private final Label nbr_vaccinLabel = new Label();
            private final Label date_vaccinLabel = new Label();
            private final Label blood_typeLabel = new Label();
            private final Label sickness_estimationLabel = new Label();

            @Override
            protected void updateItem(InfoMedicaux item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();
                    maladieLabel.setText("Maladie: " + item.getMaladie());
                    descriptionLabel.setText("Description: " + item.getDescription());
                    nbr_vaccinLabel.setText("Nombre de vaccins: " + item.getNbr_vaccin());
                    date_vaccinLabel.setText("Date du vaccin: " + item.getDate_vaccin());
                    blood_typeLabel.setText("Groupe sanguin: " + item.getBlood_type());
                    sickness_estimationLabel.setText("Estimation de la maladie: " + item.getSickness_estimation());
                    vbox.getChildren().addAll(maladieLabel, descriptionLabel, nbr_vaccinLabel, date_vaccinLabel, blood_typeLabel, sickness_estimationLabel);
                    setGraphic(vbox);
                }
            }
        });
    }

    private void chargerInfoMedicauxAssocies() {
        try {
            if (baby != null) {
                List<InfoMedicaux> infoMedicauxList = infoMedicauxServices.getByNomBaby(baby.getNom());
                ObservableList<InfoMedicaux> observableList = FXCollections.observableList(infoMedicauxList);
                infoMedicauxListView.setItems(observableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des informations médicaux.");
            alert.showAndWait();
        }
    }

    public void initData(Baby baby) {
        this.baby = baby;
        chargerInfoMedicauxAssocies();
    }
}
