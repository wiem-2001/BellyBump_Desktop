package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentService;
import tn.esprit.services.PostService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class dash implements Initializable {

    @FXML
    private TableView<Post> ProdtableView;

    @FXML
    private TableColumn<Post, String> auteur;

    @FXML
    private TableColumn<Post, String> contenu;
    @FXML
    private TableColumn<Comment, String> Cauteur;

    @FXML
    private TableView<Comment> ComTableView;
    @FXML
    private TableColumn<Comment,String> commentaire;


    @FXML
    private AnchorPane halfnav_form;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private ImageView postImage;

    @FXML
    private TableColumn<Post, String> titre;

    @FXML
    void Delete(ActionEvent event) {
        PostService ps = new PostService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmez la suppression ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ps.delete(ProdtableView.getSelectionModel().getSelectedItem());
            ProdtableView.getItems().remove(ProdtableView.getSelectionModel().getSelectedItem());
            showSuccessMessage("Post supprimé avec succes!");
        } else {
            alert.close();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

private final CommentService cs =new CommentService();
private final PostService ps = new PostService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPosts();
        showComments();
    }

    private void showComments() {
        try{
            List<Comment> comments =cs.getAll();
            ObservableList<Comment> observableList = FXCollections.observableList(comments);
            ComTableView.setItems(observableList);
            Cauteur.setCellValueFactory(new PropertyValueFactory<>("author"));
            commentaire.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void showPosts() {
        try {
            List<Post> posts = ps.getAll();
            ObservableList<Post> observableList = FXCollections.observableList(posts);
            ProdtableView.setItems(observableList);
            auteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
            titre.setCellValueFactory(new PropertyValueFactory<>("title"));
            contenu.setCellValueFactory(new PropertyValueFactory<>("content"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void deleteC(ActionEvent event) {
        CommentService cs =new CommentService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmez la suppression ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cs.delete(ComTableView.getSelectionModel().getSelectedItem());
            ComTableView.getItems().remove(ComTableView.getSelectionModel().getSelectedItem());
            showSuccessMessage("Comment supprimé avec succes!");
        } else {
            alert.close();
        }
    }
}






