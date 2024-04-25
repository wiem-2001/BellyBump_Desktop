package tn.esprit.services;

import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Produit;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitServices  {

    private final Connection cnx = MaConnexion.getInstance().getCnx();

    public void add(Produit produit) {
        String req = "INSERT INTO produit(nom, description, prix, image, partenaire_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setString(4, produit.getImagePath()); // Correction pour correspondre à l'attribut `image`
            ps.setInt(5, produit.getPartenaireId()); // Ajout du partenaire_id dans la requête d'insertion
            ps.executeUpdate();
            System.out.println("Produit ajouté avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Produit produit) {
        String req = "UPDATE produit SET nom = ?, description = ?, prix = ?, image = ?, partenaire_id = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setString(4, produit.getImagePath()); // Correction pour correspondre à l'attribut `image`
            ps.setInt(5, produit.getPartenaireId()); // Mise à jour pour inclure partenaire_id
            ps.setInt(6, produit.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit mis à jour avec succès");
            } else {
                System.out.println("Aucun produit trouvé avec cet ID pour la mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... autres méthodes ...

    // Méthode pour récupérer un seul produit par ID
    public Produit getOne(int id) {
        String req = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
                produit.setDescription(rs.getString("description"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setImagePath(rs.getString("image")); // Correction pour correspondre à l'attribut `image`
                produit.setPartenaireId(rs.getInt("partenaire_id")); // Ajout de la récupération de partenaire_id
                return produit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si le produit n'est pas trouvé
    }


    // ... autres méthodes ...
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNom(rs.getString("nom"));
                produit.setDescription(rs.getString("description"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setImagePath(rs.getString("image"));
                produit.setPartenaireId(rs.getInt("partenaire_id"));
                produits.add(produit);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all produits: " + e.getMessage());
        }
        return produits;
    }

///pour la creation de panier
    public Produit findByName(String nom) {
        String sql = "SELECT * FROM produit WHERE nom = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return extractProduitFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si aucun produit n'est trouvé ou en cas d'erreur
    }

    // This helper method extracts a Produit from the current row of a ResultSet.
    private Produit extractProduitFromResultSet(ResultSet rs) throws SQLException {
        Produit produit = new Produit();
        produit.setId(rs.getInt("id"));
        produit.setNom(rs.getString("nom"));
        produit.setDescription(rs.getString("description"));
        produit.setPrix(rs.getDouble("prix"));
        produit.setImagePath(rs.getString("image"));
        produit.setPartenaireId(rs.getInt("partenaire_id"));
        // Dans ProduitServices.findByName, juste avant de retourner le produit


        return produit;
    }




    public void delete(Produit produit) {
        String requete = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, produit.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Produit deleted successfully!");
            } else {
                System.out.println("No produit found with this ID for deletion.");
            }
        } catch (SQLException e) {
            System.out.println("Error during partenaire deletion: " + e.getMessage());
        }
    }
////fin creation panier dans la methode addproduct dans cartServices//////

}
