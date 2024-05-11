package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;

public class UpdatePartenaire {

    @FXML
    private TextField TFNom;

    @FXML
    private TextField TFMarque;

    @FXML
    private TextField TFEmail;

    @FXML
    private TextField TFDescription;

    private Partenaire selectedPartenaire;
    private final PartenaireServices partenaireService = new PartenaireServices(); // Assurez-vous que cette classe existe dans votre application

    public void initData(Partenaire partenaire) {
        this.selectedPartenaire = partenaire;
        TFNom.setText(partenaire.getNom());
        TFMarque.setText(partenaire.getMarque());
        TFEmail.setText(partenaire.getEmail());
        TFDescription.setText(partenaire.getDescription());
    }

    @FXML
    void updatePartenaire(ActionEvent event) {
        selectedPartenaire.setNom(TFNom.getText());
        selectedPartenaire.setMarque(TFMarque.getText());
        selectedPartenaire.setEmail(TFEmail.getText());
        selectedPartenaire.setDescription(TFDescription.getText());

        partenaireService.update(selectedPartenaire); // Assurez-vous d'implémenter la méthode update dans votre service
        showAlert(Alert.AlertType.INFORMATION, "Modification Réussie", null, "Les modifications ont été enregistrées avec succès");
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
