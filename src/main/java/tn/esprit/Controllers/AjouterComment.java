package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Comment;
import tn.esprit.services.CommentService;
import tn.esprit.services.PostService;

public class AjouterComment {
    @FXML
    private TextField auteurTF;

    @FXML
    private TextArea contenuTA;

    private int postId; // L'ID du post auquel ce commentaire est associé
    PostService ps =new PostService();

    // Méthode pour initialiser les données du post associé
    public void initPost(int postId) {

        this.postId = postId;
    }
    CommentService cs =new CommentService();
    @FXML
    void ajouterCommentaire(ActionEvent event) {

        // Création d'un nouvel objet Comment avec les données du formulaire
        Comment nouveauCommentaire = new Comment();
        nouveauCommentaire.setAuthor(auteurTF.getText());
        nouveauCommentaire.setContenu(contenuTA.getText());
        int postId = 1;
        nouveauCommentaire.setPostId(postId); // Définition de l'ID du post associé

        // Ajout du commentaire à la base de données en utilisant le service CommentService
        CommentService commentService = new CommentService();
        commentService.add(nouveauCommentaire);

        // Fermeture de la fenêtre après l'ajout du commentaire
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
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
}
