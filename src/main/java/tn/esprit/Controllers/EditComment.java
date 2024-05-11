package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Comment;
import tn.esprit.services.CommentService;

public class EditComment {

    @FXML
    private TextField auteurTF;

    @FXML
    private TextArea contenuTA;

    private Comment selectedComment;
    private final CommentService cs = new CommentService();
    public void initData(Comment comment)
    {
        this.selectedComment=comment;
        auteurTF.setText(comment.getAuthor());
        contenuTA.setText(comment.getContenu());
    }

    @FXML
    void modifierC(ActionEvent event) {
selectedComment.setAuthor(auteurTF.getText());
selectedComment.setContenu(contenuTA.getText());
        cs.update(selectedComment);
        showAlert(Alert.AlertType.INFORMATION,"Modification Reussie",null,"Les modifications ont été enregistrées avec succes");

    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CommentFeed.fxml"));
            auteurTF.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
