package tn.esprit.services;

import tn.esprit.entities.Partenaire;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartenaireServices implements IService<Partenaire> {

    private final Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Partenaire partenaire) {
        String req = "INSERT INTO `partenaire` (`nom`, `marque`, `email`, `description`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, partenaire.getNom());
            ps.setString(2, partenaire.getMarque());
            ps.setString(3, partenaire.getEmail());
            ps.setString(4, partenaire.getDescription());
            ps.executeUpdate();
            System.out.println("Partenaire added successfully.");
        } catch (SQLException e) {
            System.out.println("Error when adding partenaire: " + e.getMessage());
        }
    }

    @Override
    public void update(Partenaire partenaire) {
        String req = "UPDATE `partenaire` SET `nom` = ?, `marque` = ?, `email` = ?, `description` = ? WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, partenaire.getNom());
            ps.setString(2, partenaire.getMarque());
            ps.setString(3, partenaire.getEmail());
            ps.setString(4, partenaire.getDescription());
            ps.setInt(5, partenaire.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Partenaire updated successfully.");
            } else {
                System.out.println("No rows were updated. Check the partenaire ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating partenaire: " + e.getMessage());
        }
    }

    @Override
    public void delete(Partenaire partenaire) {
        String requete = "DELETE FROM partenaire WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, partenaire.getId());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Partenaire deleted successfully!");
            } else {
                System.out.println("No partenaire found with this ID for deletion.");
            }
        } catch (SQLException e) {
            System.out.println("Error during partenaire deletion: " + e.getMessage());
        }
    }

    @Override
    public List<Partenaire> getAll() {
        List<Partenaire> partenaires = new ArrayList<>();
        String req = "SELECT * FROM partenaire";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {
            while (res.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId(res.getInt("id"));
                partenaire.setNom(res.getString("nom"));
                partenaire.setMarque(res.getString("marque"));
                partenaire.setEmail(res.getString("email"));
                partenaire.setDescription(res.getString("description"));
                partenaires.add(partenaire);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all partenaires: " + e.getMessage());
        }
        return partenaires;
    }

    @Override
    public Partenaire getOne(int id) {
        String req = "SELECT * FROM partenaire WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    Partenaire partenaire = new Partenaire();
                    partenaire.setId(res.getInt("id"));
                    partenaire.setNom(res.getString("nom"));
                    partenaire.setMarque(res.getString("marque"));
                    partenaire.setEmail(res.getString("email"));
                    partenaire.setDescription(res.getString("description"));
                    return partenaire;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting one partenaire: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Partenaire getOne(String email) {
        return null;
    }
}
