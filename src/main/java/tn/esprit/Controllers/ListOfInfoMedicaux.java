package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.services.InfoMedicauxServices;
import tn.esprit.services.PdfService;
import tn.esprit.util.MaConnexion;

import javafx.stage.FileChooser;
import java.io.File;
import java.sql.Connection;

public class ListOfInfoMedicaux {

    @FXML
    private ListView<InfoMedicaux> infoMedicauxListView;

    private final InfoMedicauxServices infoMedicauxServices;
    private final PdfService pdfService;

    public ListOfInfoMedicaux() {
        Connection connection = MaConnexion.getInstance().getCnx();
        infoMedicauxServices = new InfoMedicauxServices(connection);
        pdfService = new PdfService();
    }

    @FXML
    void initialize() {
        ObservableList<InfoMedicaux> observableList = FXCollections.observableList(infoMedicauxServices.getAll());
        infoMedicauxListView.setItems(observableList);
        infoMedicauxListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(InfoMedicaux item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Button savePdfButton = new Button("Save PDF");
                    savePdfButton.setOnAction(e -> generatePdfForSelectedInfoMedicaux(item));

                    VBox vbox = new VBox(
                            new Label("Maladie: " + item.getMaladie()),
                            new Label("Description: " + item.getDescription()),
                            new Label("Nombre de vaccins: " + item.getNbr_vaccin()),
                            new Label("Date du vaccin: " + item.getDate_vaccin().toString()),
                            new Label("Groupe sanguin: " + item.getBlood_type()),
                            new Label("Estimation de la maladie: " + item.getSickness_estimation()),
                            savePdfButton
                    );
                    setGraphic(vbox);
                }
            }
        });
    }

    private void generatePdfForSelectedInfoMedicaux(InfoMedicaux infoMedicaux) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("InfoMedicauxDetails_" + infoMedicaux.getId() + ".pdf");

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            pdfService.createPdf(file, infoMedicaux);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setHeaderText(null);
            alert.setContentText("PDF has been successfully generated and saved to: " + file.getPath());
            alert.showAndWait();
        }
    }
}
