package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.entities.Post;
import tn.esprit.entities.User;
import tn.esprit.services.PostService;
import tn.esprit.services.UserServices;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PostCell {

    @FXML
    private Label auteurL;

    @FXML
    private Label contenuL;

    @FXML
    private ImageView imageP;

    @FXML
    private ImageView imgReaction;
    @FXML
    private Label reactionName;
    UserServices us=new UserServices();
    String userEmail= MainFX.getLoggedInUserEmail();
    User user=us.getOne(userEmail);



    @FXML
    private Label titreL;
    @FXML
    private ImageView imgDislike;

    @FXML
    private ImageView imgLike;

    @FXML
    private HBox likeContainer;

    @FXML
    private Button updateB;

    @FXML
    private Button deleteB;

    @FXML
    private HBox reactionsContainer;
    private Post poste;
    private long startTime=0;



    public void setData(Post post) {
        // Attempt to load the image and handle possible errors.
       if (post ==null){
           throw new IllegalArgumentException("Post cannot be null");
       }
       this.poste=post;
       String imagePath = convertToFileSystemPath(post.getImage());

       if (imagePath!=null ) {
           try (InputStream is = getClass().getResourceAsStream(imagePath)) {
               if (is != null) {


                   Image image = new Image(is);
                   imageP.setImage(image);
               } else {
                   System.out.println("Image Not Found:" + imagePath);
               }
           } catch (IOException e) {
               System.err.println("Error loading image: " + e.getMessage());           }
       }else{
           System.out.println("Image path is null");
       }
           auteurL.setText(post.getAuteur());
        titreL.setText(post.getTitle());
        contenuL.setText(post.getContent());
        if (post.getAuteur().equals(user.getFirst_name())) {
            updateB.setVisible(true);
            deleteB.setVisible(true);
        } else {
            updateB.setVisible(false);
            deleteB.setVisible(false);
        }

        if(post == null){
            throw new IllegalArgumentException("Le post ne peut pas etre null");
        }
    }

    public String convertToFileSystemPath(String imagePath) {
        if (imagePath != null && imagePath.startsWith("/Images/")) {
            return imagePath; // Si le chemin est déjà relatif au dossier "Images", le retourner tel quel
        } else {
            return "/Images/" + imagePath; // Sinon, préfixez le chemin avec "/Images/"
        }
    }


    @FXML
    void CommentP(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CommentFeed.fxml"));
            auteurL.getScene().setRoot(root);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void DeleteP(ActionEvent event) {
        try {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmez la suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce post?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, supprimer le post
                PostService ps = new PostService();
                ps.delete(poste); // Utilisez votre service pour supprimer le post
                showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Post supprimé avec succès!");
                // Si vous souhaitez effectuer une action supplémentaire après la suppression, vous pouvez le faire ici
            }
        } catch (Exception e) {
            // Gérer toute exception qui pourrait survenir pendant la suppression
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du post", e.getMessage());
        }
    }
    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void UpdateP(ActionEvent event) {
        try {
            // Charger la vue de modification du post
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditPost.fxml"));
            Parent editPostView = loader.load();

            // Récupérer le contrôleur de la vue de modification
            EditPost editPostController = loader.getController();
            // Passer le post à modifier au contrôleur de la vue de modification
            editPostController.initData(poste);

            // Afficher la vue de modification dans une nouvelle fenêtre
            Scene editScene = new Scene(editPostView);
            Stage window = new Stage();
            window.setScene(editScene);
            window.setTitle("Modifier le post");
            window.showAndWait(); // Attendre que la fenêtre de modification soit fermée

            // Mettre à jour l'affichage après la modification
            // Vous pouvez mettre à jour l'affichage ici selon votre logique
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du post", e.getMessage());
        }
    }

    public void onlikeContainerPressed(javafx.scene.input.MouseEvent mouseEvent)  {
        startTime = System.currentTimeMillis();
    }

    public void onLikeContainerMouseReleased(javafx.scene.input.MouseEvent mouseEvent) {

            if (System.currentTimeMillis() - startTime > 500) {
                reactionsContainer.setVisible(true);
            }

        }
    public void setReaction(Reaction reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());


    }

    public void onReactionImgPressed(javafx.scene.input.MouseEvent me) {

        switch (((ImageView) me.getSource()).getId()){
            case "imgLike" :
                setReaction(Reaction.Like);
                break;
            case "imgDislike" :
                setReaction(Reaction.Dislike);
        }
        reactionsContainer.setVisible(false);

    }
    /**
     * Initialize the controller and set up initial data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData(getPost());
    }
    */
}
