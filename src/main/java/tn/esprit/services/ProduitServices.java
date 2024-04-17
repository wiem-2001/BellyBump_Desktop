package tn.esprit.services;
import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Produit;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProduitServices {

    // Attribut pour la connexion
    private final Connection cnx = MaConnexion.getInstance().getCnx();

    public void add(Produit produit) {
        String req = "INSERT INTO produit(nom, description, prix, quantiteDisponible, image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setInt(4, produit.getQuantiteDisponible());
            ps.setBytes(5, produit.getImage()); // Ajoutez cette ligne pour gérer l'image
            ps.executeUpdate();
            System.out.println("Produit ajouté avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(Produit produit) {
        String req = "UPDATE produit SET nom = ?, description = ?, prix = ?, quantiteDisponible = ? WHERE idProd = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            ps.setString(2, produit.getDescription());
            ps.setDouble(3, produit.getPrix());
            ps.setInt(4, produit.getIdProd());
            ps.setInt(5, produit.getQuantiteDisponible());

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

    public void delete(Produit produit) {
        String req = "DELETE FROM produit WHERE nom = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, produit.getNom());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit supprimé avec succès");
            } else {
                System.out.println("Aucun produit trouvé avec cet ID pour la suppression.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String req = "Select * from produit";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Produit produit = new Produit();
                produit.setIdProd(res.getInt("idProd"));
                produit.setNom(res.getString(2));
                produit.setDescription(res.getString(3));
                produit.setPrix(res.getDouble(4));
                produit.setQuantiteDisponible(res.getInt(5));
                produits.add(produit);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produits;
    }

    // Méthode pour récupérer un seul produit par ID (non implémentée dans PartenaireService, mais utile à avoir)
    public Produit getOne(int id) {
        String req = "SELECT * FROM produit WHERE idProd = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProd(rs.getInt("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setDescription(rs.getString("description"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setQuantiteDisponible(rs.getInt("quantiteDisponible"));
                return produit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si le produit n'est pas trouvé
    }

    ////Gerer le stock *******************************************************************************************
    public void augmenterStock(int idProd, int quantite) {
        String req = "UPDATE produit SET quantiteDisponible = quantiteDisponible + ? WHERE idProd = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, quantite);
            ps.setInt(2, idProd);
            ps.executeUpdate();
            System.out.println("Stock augmenté avec succès pour le produit ID " + idProd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void diminuerStock(int idProd, int quantite) {
        String req = "UPDATE produit SET quantiteDisponible = GREATEST(0, quantiteDisponible - ?) WHERE idProd = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, quantite);
            ps.setInt(2, idProd);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Stock diminué avec succès pour le produit ID " + idProd);
            } else {
                System.out.println("Aucun produit trouvé avec cet ID pour diminuer le stock.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ////Gerer le stock *******************************************************************************************

////à corigeé
    public void acheterProduitAvecPromotion(int idProd, int quantiteAchetee) {
        try {
            double prixUnitaire = getPrixUnitaireProduit(idProd);

            int quantiteFacturable = quantiteAchetee - (quantiteAchetee / 3);
            double prixTotal = prixUnitaire * quantiteFacturable;

            System.out.println("Promotion appliquée : Achetez 2, obtenez 1 gratuit.");
            System.out.println("Quantité achetée : " + quantiteAchetee);
            System.out.println("Quantité facturable : " + quantiteFacturable);
            System.out.println("Prix unitaire : " + prixUnitaire);
            System.out.println("Prix total à payer : " + prixTotal);

            // Mise à jour du stock
            diminuerStock(idProd, quantiteAchetee);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getPrixUnitaireProduit(int idProd) throws SQLException {
        String query = "SELECT prix FROM produit WHERE idProd = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idProd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("prix");
                } else {
                    throw new SQLException("Produit non trouvé avec ID: " + idProd);
                }
            }
        }
    }

    private void diminuerStockAchat(int idProd, int quantiteAchetee) throws SQLException {
        String updateQuery = "UPDATE produit SET quantiteDisponible = quantiteDisponible - ? WHERE idProd = ?";
        try (PreparedStatement ps = cnx.prepareStatement(updateQuery)) {
            ps.setInt(1, quantiteAchetee);
            ps.setInt(2, idProd);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Mise à jour du stock échouée, aucun produit trouvé avec ID: " + idProd);
            }
            System.out.println("Stock mis à jour pour le produit ID " + idProd);
        }
    }
}
