package tn.esprit.controllers;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentService;

import java.io.IOException;
import java.util.Optional;

public class AfficherComment {

    @FXML
    private Button btnModifier;

    @FXML
    private ListView<Comment> listViewComment;

    private CommentService cs = new CommentService();

    @FXML
    public void initialize()
    {
        ObservableList<Comment> comments = FXCollections.observableArrayList(cs.getAll());
        listViewComment.setItems(comments);
    }
    private void showAlert(Alert.AlertType aType, String ti, String head, String cont) {
        Alert alert = new Alert(aType);
        alert.setTitle(ti);
        alert.setHeaderText(head);
        alert.setContentText(cont);
        alert.showAndWait();
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void modifierComment(ActionEvent event) {
        Comment selectedComment = listViewComment.getSelectionModel().getSelectedItem();
        if(selectedComment == null)
        {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun post sélectionné", "Veuillez sélectionner un post à modifier.");
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditComment.fxml"));
            Parent edit = loader.load();
            EditComment editComment = loader.getController();
            editComment.initData(selectedComment);

            // Afficher la fenêtre de modification
            Scene editScene = new Scene(edit);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(editScene);
            window.setHeight(800); window.setMaxHeight(800); window.setMinHeight(800);
            window.setWidth(800); window.setMaxWidth(800); window.setMinWidth(800);
            window.setTitle("Belly Bump | Modifier un comment");
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void supprimerComment(ActionEvent event) {
        CommentService cs = new CommentService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmez la suppression");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.delete(listViewComment.getSelectionModel().getSelectedItem());
            listViewComment.getItems().remove(listViewComment.getSelectionModel().getSelectedItem());
            showSuccessMessage("Comment Supprimé avec succes!");

        }
        else{
            alert.close();
        }

    }

}