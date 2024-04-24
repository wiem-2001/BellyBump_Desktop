package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Etablissement;
import tn.esprit.entities.Medcin;
import tn.esprit.services.EtablissementServices;
import tn.esprit.services.MedcinServices;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ModifierMedcin {

    @FXML
    private TextField nom;

    @FXML
    private ComboBox<String> etablissementNom;
    @FXML
    private TextField prenom;

    @FXML
    private TextField specialite;
    private EtablissementServices etablissementServices;
    private final MedcinServices medcinServices;
    private Medcin medcin;
    @FXML
    private void initialize() {
        // Assuming you have a database connection available
        Connection connection = MaConnexion.getInstance().getCnx();
        etablissementServices = new EtablissementServices(connection);
        List<String> etablissementNames = etablissementServices.getAllEtablissementNames();
        etablissementNom.getItems().addAll(etablissementNames);
    }

    public ModifierMedcin() {
        Connection connection = MaConnexion.getInstance().getCnx();
        medcinServices = new MedcinServices(connection);
    }

    public void initData(Medcin medcin) {
        this.medcin = medcin;
        nom.setText(medcin.getNom());
        prenom.setText(medcin.getPrenom());
        specialite.setText(medcin.getSpecialite());

    }

    @FXML
    void modifierMedcin(ActionEvent event) {
        try {
            String nouveauNom = nom.getText();
            String nouveauPrenom = prenom.getText();
            String nouvelleSpecialite = specialite.getText();
            String selectedEtablissement = etablissementNom.getValue();

            medcin.setNom(nouveauNom);
            medcin.setPrenom(nouveauPrenom);
            medcin.setSpecialite(nouvelleSpecialite);
            medcin.setEtab_nom(selectedEtablissement);

            // Utiliser le nom de l'établissement pour appeler la méthode update
            medcinServices.update(medcin, selectedEtablissement);
// Fermer la fenêtre de modification après la mise à jour réussie
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Recharger la vue des établissements après la modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MedcinBack.fxml"));
            Parent root = loader.load();
            MedcinBack listController = loader.getController();
            listController.initialize();

            Scene scene = new Scene(root);
            Stage listStage = new Stage();
            listStage.setScene(scene);
            listStage.setTitle("Liste des medcins");
            listStage.show();
            // Le reste du code reste inchangé...
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement de la vue.");
            alert.showAndWait();
        }
    }
    private List<Etablissement> listeEtablissements;
  // Méthode pour initialiser listeEtablissements
    public void initialiserListeEtablissements(List<Etablissement> listeEtablissements) {
        this.listeEtablissements = listeEtablissements;
    }
    private int obtenirEtablissementSelectionne() {
        // Récupérer le nom de l'établissement sélectionné à partir du ComboBox
        String nomEtablissementSelectionne = etablissementNom.getValue();

        // Recherche de l'ID de l'établissement sélectionné dans listeEtablissements
        for (Etablissement etablissement : listeEtablissements) {
            if (etablissement.getNom().equals(nomEtablissementSelectionne)) {
                return etablissement.getId();
            }
        }

        // Si aucun établissement ne correspond au nom sélectionné, vous pouvez retourner une valeur par défaut ou lancer une exception
        // Dans cet exemple, nous retournons simplement 0, mais vous devriez ajuster cela en fonction de votre logique
        return 0;
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
}
