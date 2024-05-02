package tn.esprit.services;

import tn.esprit.entities.Medcin;
import tn.esprit.interfaces.IServiceMedcin;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedcinServices implements IServiceMedcin<Medcin> {
    private final Connection connection;

    public MedcinServices() {
        this.connection = MaConnexion.getInstance().getCnx();
    }

    public MedcinServices(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Medcin medcin, String etablissementNom) {
        String query = "INSERT INTO medcins (etab_nom, nom, prenom, specialite) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, etablissementNom);
            stmt.setString(2, medcin.getNom());
            stmt.setString(3, medcin.getPrenom());
            stmt.setString(4, medcin.getSpecialite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Medcin medcin, String etablissementNom) {
        String query = "UPDATE medcins SET nom = ?, prenom = ?, specialite = ?, etab_nom = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, medcin.getNom());
            stmt.setString(2, medcin.getPrenom());
            stmt.setString(3, medcin.getSpecialite());
            stmt.setString(4, etablissementNom); // Utilisez le nom de l'établissement
            stmt.setInt(5, medcin.getId()); // Utilisez l'ID du médecin
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllMedcinNames() {
        List<String> medcinNames = new ArrayList<>();
        try {
            String query = "SELECT nom FROM medcins";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("nom");
                medcinNames.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medcinNames;
    }
    @Override
    public void delete(Medcin medcin) {
        String query = "DELETE FROM medcins WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, medcin.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medcin> getAll() {
        List<Medcin> medcins = new ArrayList<>();
        String query = "SELECT * FROM medcins";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                String etab_nom=rs.getString("etab_nom");
                medcins.add(new Medcin(id, nom, prenom, specialite,etab_nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medcins;
    }


    public List<Medcin> getByNomEtab(String nomEtab) {
        List<Medcin> medcins = new ArrayList<>();
        String query = "SELECT * FROM medcins WHERE etab_nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomEtab);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                medcins.add(new Medcin(id, nom, prenom, specialite, nomEtab));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medcins;
    }


    @Override
    public Medcin getOne(int id) {
        // Méthode non implémentée car l'interface l'exige, mais n'utilise pas l'ID de l'établissement
        return null;
    }
}
