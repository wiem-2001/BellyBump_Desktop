package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.Post;
import tn.esprit.entities.User;
import tn.esprit.services.ChatGPTClient;
import tn.esprit.services.PostService;
import tn.esprit.services.UserServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class AjouterPost {
    @FXML
    private TextField auteurTF;

    @FXML
    private TextArea contenuTF;

    @FXML
    private TextField imageTF;

    @FXML
    private TextField titreTF;
    private final PostService ps = new PostService();
    private String imagePath;
    final UserServices us = new UserServices();
    String userEmail= MainFX.getLoggedInUserEmail();
    User user=us.getOne(userEmail);

    @FXML
    void AjouterP(ActionEvent event) {

        try {
            String relativeImagePath = "Images/" + new File(imagePath).getName();
            if (ps.containsInappropriate(contenuTF.getText())||ps.containsInappropriate(titreTF.getText())) {
                throw new IllegalArgumentException("Le titre ou le contenu du post contient des mots inappropriés.");
            }
            boolean containsInappropriateWords = ChatGPTClient.containsInappropriateWords(contenuTF.getText());

            if (containsInappropriateWords) {
                // Affichage d'une alerte indiquant que le contenu contient des mots inappropriés
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le titre ou lecontenu contient des mots inappropriés !");
                alert.showAndWait();
                return; // Arrêter l'ajout du post
            }
            ps.add(new Post(titreTF.getText(), user.getFirst_name(), contenuTF.getText(), relativeImagePath));
            // Affichage d'un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le post a été ajouté avec succès !");
            alert.showAndWait();

            // Effacement des champs de saisie après l'ajout réussi (si nécessaire)
            titreTF.clear();
            auteurTF.clear();
            contenuTF.clear();
            imagePath = null; // Réinitialisation du chemin de l'image
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!!!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Feed.fxml"));
            auteurTF.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Définir le répertoire cible dans les ressources
            String targetDirectory = "src/main/resources/Images/";

            try {
                // Créer le répertoire cible s'il n'existe pas
                File directory = new File(targetDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Copier le fichier sélectionné vers le répertoire cible
                Path sourcePath = selectedFile.toPath();
                Path targetPath = new File(targetDirectory + selectedFile.getName()).toPath();
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                // Mettre à jour le chemin de l'image avec le chemin relatif du fichier dans les ressources
                imagePath = targetPath.toString().replace("\\", "/").replace("src/main/resources/", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
