package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentService implements IService<Comment> {
    private final Connection connection = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Comment comment) {
        if (comment.getAuthor() == null || comment.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("L'auteur du commentaire est requis");
        }

        if (comment.getContenu() == null || comment.getContenu().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du commentaire est requis");
        }

        if (comment.getPostId() <= 0) {
            throw new IllegalArgumentException("Le postId du commentaire est invalide");
        }

        String req = "INSERT INTO `comment`( `author`, `contenu`, `postId`) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, comment.getAuthor());
            st.setString(2, comment.getContenu());
            st.setInt(3, comment.getPostId());
            st.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du commentaire: " + e.getMessage());
        }
    }


    @Override
    public void update(Comment Comment) {
       String req = "UPDATE Comment SET author =? , contenu=?  WHERE id = ?";
       try {
           PreparedStatement cs = connection.prepareStatement(req);
           cs.setString(1,Comment.getAuthor());
           cs.setString(2,Comment.getContenu());
           cs.setInt(3,Comment.getId());
           int rowsUpdated = cs.executeUpdate();
           if (rowsUpdated > 0) {
               System.out.println("Comment updated successfully");
           } else {
               System.out.println("No Comment found with ID: " + Comment.getId());
           }
       } catch (SQLException e) {
           e.printStackTrace();
           // Handle exceptions appropriately
       }
    }

    @Override
    public void delete(Comment comment) {
        String query = "DELETE FROM comment WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, comment.getId());
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie du commentaire avec l'ID : " + comment.getId());
            } else {
                System.out.println("Aucun commentaire trouvé avec l'ID : " + comment.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du commentaire: " + e.getMessage());
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM `comment`";
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Comment comment = new Comment();
                comment.setId(res.getInt("id"));
                comment.setAuthor(res.getString("author"));
                comment.setContenu(res.getString("contenu"));
                comment.setPostId(res.getInt("postId"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commentaires: " + e.getMessage());
        }
        return comments;
    }

    @Override
    public Comment getOne(int id) {
        Comment comment = null;
        String query = "SELECT * FROM comment WHERE id = ?";
        try  {
            PreparedStatement st =connection.prepareStatement(query);
            st.setInt(1,id);
            ResultSet res = st.executeQuery();
            if(res.next()){
                comment = new Comment();
                comment.setId(res.getInt("id"));
                comment.setAuthor(res.getString("author"));
                comment.setContenu(res.getString("contenu"));
                comment.setPostId(res.getInt("postId"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'envoi de la requête : " + e.getMessage());
        }
        return comment;
    }

    @Override
    public Comment getOne(String email) {
        return null;
    }

    public boolean containsInappropriate1(String text) {
        List<String> inappropriateWords = Arrays.asList("debile", "malin","merde", "puatin","tuer","massacre","violence","haine","drogue","alcool","harceler"); // Remplacez ceci par votre liste de mots inappropriés

        for (String word : inappropriateWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

