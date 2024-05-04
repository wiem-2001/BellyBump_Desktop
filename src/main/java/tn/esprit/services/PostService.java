package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.entities.Post;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostService implements IService<Post> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();

    //actions
    @Override
    public void add(Post post) {
        // Vérifier si le titre du post est vide ou null
        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Le titre du post est requis");
        }

        // Vérifier si l'auteur du post est vide ou null
        if (post.getAuteur() == null || post.getAuteur().isEmpty()) {
            throw new IllegalArgumentException("L'auteur du post est requis");
        }

        // Vérifier si le contenu du post est vide ou null
        if (post.getContent() == null || post.getContent().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du post est requis");
        }

        // Vérifier si l'image du post est vide ou null
        if (post.getImage() == null || post.getImage().isEmpty()) {
            throw new IllegalArgumentException("L'image du post est requise");
        }

        // Insérer le post dans la base de données
        String req = "INSERT INTO `post`( `title`, `auteur`, `content`, `image`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, post.getTitle());
            st.setString(2, post.getAuteur());
            st.setString(3, post.getContent());
            st.setString(4, post.getImage());
            st.executeUpdate();
            System.out.println("Post ajouté avec succès");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du post: " + e.getMessage());
        }
    }


    @Override
    public void update(Post post) {
      String req = "UPDATE post SET auteur=?, title =?, content=?, image=? WHERE id =?";
         try{
             PreparedStatement ps=cnx.prepareStatement(req);
             ps.setString(1,post.getAuteur());
             ps.setString(2,post.getTitle());
             ps.setString(3,post.getContent());
             ps.setString(4,post.getImage());
             ps.setInt(5,post.getId());
             int rowsUpdated = ps.executeUpdate();
             if(rowsUpdated > 0) {
             System.out.println("Post Updated Successfully");
             }
             else{
                 System.out.println("No post found with ID :"+post.getId());
             }


         } catch (SQLException e) {
            e.printStackTrace();
         }
    }


    @Override
    public void delete(Post post) {
        String query = "DELETE FROM post WHERE title = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, post.getTitle());

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie du post avec le titre : " + post.getTitle());
            } else {
                System.out.println("Aucun post trouvé avec le titre : " + post.getTitle());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'envoi de la requête : " + e.getMessage());
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM `post`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Post post = new Post();
                post.setId(res.getInt("id"));
                post.setTitle(res.getString("title"));
                post.setAuteur(res.getString("auteur"));
                post.setContent(res.getString("content"));
                post.setImage(res.getString("image"));
                posts.add(post);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public Post getOne(int id) {

        String req = "SELECT * FROM post WHERE id = ?";
        Post  post =null;
        try  {
            PreparedStatement st =cnx.prepareStatement(req);
            st.setInt(1,id);
            ResultSet res = st.executeQuery();
           if(res.next()){
               post = new Post();
               post.setId(res.getInt("id"));
               post.setAuteur(res.getString("auteur"));
               post.setTitle(res.getString("title"));
               post.setContent(res.getString("content"));
               post.setImage(res.getString("image"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'envoi de la requête : " + e.getMessage());
        }
        return post;
    }
    public boolean containsInappropriate(String text) {
        List<String> inappropriateWords = Arrays.asList("debile", "malin","merde", "puatin","tuer","massacre","violence","haine","drogue","alcool","harceler"); // Remplacez ceci par votre liste de mots inappropriés

        for (String word : inappropriateWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


}