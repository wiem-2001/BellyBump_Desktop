package tn.esprit.services;

import tn.esprit.entities.Etablissement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.entities.Medcin;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

public class EtablissementServices implements IService<Etablissement> {
    private Connection connection;

    public EtablissementServices(Connection connection) {
        this.connection = connection;
    }
    private final Connection cnx= MaConnexion.getInstance().getCnx();

    public EtablissementServices() {

    }

    public void updateRating(int etablissementId, int rating) throws SQLException {
        String sql = "UPDATE etab SET rating = ? WHERE id = ?";
        try (Connection connection = MaConnexion.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rating);
            statement.setInt(2, etablissementId);
            statement.executeUpdate();
        }
    }

    public void add(Etablissement etablissement) {
        String query = "INSERT INTO etab (nom, type, localisation) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, etablissement.getNom());
            stmt.setString(2, etablissement.getType());
            stmt.setString(3, etablissement.getLocalisation());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ...
    public void updateRating(int etablissementId, float rating) throws SQLException {
        if (connection.isClosed()) {
            connection = MaConnexion.getInstance().getCnx();
        }
        String sql = "UPDATE etab SET rating = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setFloat(1, rating);
            statement.setInt(2, etablissementId);
            statement.executeUpdate();
        }
    }


    public List<Etablissement> getAllEtablissements() {
            List<Etablissement> etablissements = new ArrayList<>();
            String query = "SELECT id, nom FROM etab";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    etablissements.add(new Etablissement(id, nom));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return etablissements;
        }



    @Override
    public void update(Etablissement etablissement) {
        String query = "UPDATE etab SET nom = ?, type = ?, localisation = ? WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, etablissement.getNom());
            stmt.setString(2, etablissement.getType());
            stmt.setString(3, etablissement.getLocalisation());
            stmt.setInt(4, etablissement.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Etablissement etablissement) {
        String query = "DELETE FROM etab WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, etablissement.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Etablissement> getAll() {
        List<Etablissement> etablissements = new ArrayList<>();
        try {
            String query = "SELECT * FROM etab";
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String type = rs.getString("type");
                String localisation = rs.getString("localisation");
                float rating = rs.getFloat("rating");
                etablissements.add(new Etablissement(id, nom, type, localisation, rating));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etablissements;
    }


    public List<String> getAllEtablissementNames() {
        List<String> etablissementNames = new ArrayList<>();
        try {
            String query = "SELECT nom FROM etab";
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("nom");
                etablissementNames.add(nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etablissementNames;
    }


    @Override
    public Etablissement getOne(int id) {
        String query = "SELECT * FROM etab WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String type = rs.getString("type");
                String localisation = rs.getString("localisation");
                return new Etablissement(id, nom, type, localisation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Etablissement> getByType(String type) {
        List<Etablissement> etablissements = new ArrayList<>();
        String query = "SELECT * FROM etab WHERE type = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String localisation = rs.getString("localisation");
                etablissements.add(new Etablissement(id, nom, type, localisation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etablissements;
    }


}
