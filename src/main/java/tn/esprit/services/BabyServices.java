package tn.esprit.services;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Baby;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

public class BabyServices implements IService<Baby> {

    public BabyServices(Connection connection) {}

    public BabyServices() {
    }

    @Override
    public void add(Baby b) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("INSERT INTO baby(nom,prenom,sexe,date_naissance,poids,taille) VALUES (?,?,?,?,?,?)")) {
            preparedStatement.setString(1, b.getNom());
            preparedStatement.setString(2, b.getPrenom());
            preparedStatement.setString(3, b.getSexe());
            preparedStatement.setDate(4, Date.valueOf(b.getDateNaissance()));
            preparedStatement.setDouble(5, b.getPoids());
            preparedStatement.setDouble(6, b.getTaille());

            preparedStatement.executeUpdate();
            System.out.println("Baby added successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public  List<Baby> getAll() {
        List<Baby> myList= new ArrayList<>();
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM baby");
            while (resultSet.next()){
                Baby c = new Baby();
                c.setId(resultSet.getInt("id"));
                c.setNom(resultSet.getString("nom"));
                c.setPrenom(resultSet.getString("prenom"));
                c.setSexe(resultSet.getString("sexe"));
                c.setDateNaissance(resultSet.getDate("date_naissance").toLocalDate());
                c.setPoids(resultSet.getFloat("poids"));
                c.setTaille(resultSet.getFloat("taille"));

                myList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return myList;
    }

    @Override
    public Baby getOne(int id) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("SELECT * FROM baby WHERE id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Baby baby = new Baby();
                baby.setId(resultSet.getInt("id"));
                baby.setNom(resultSet.getString("nom"));
                baby.setPrenom(resultSet.getString("prenom"));
                baby.setSexe(resultSet.getString("sexe"));
                baby.setDateNaissance(resultSet.getDate("date_naissance").toLocalDate());
                baby.setPoids(resultSet.getFloat("poids"));
                baby.setTaille(resultSet.getFloat("taille"));
                return baby;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // If no baby found with the given ID, return null
    }

    @Override
    public Baby getOne(String email) {
        return null;
    }

    public List<String> getAllBabyName() {
        List<String> babyNames = new ArrayList<>();
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT nom FROM baby");
            while (resultSet.next()) {
                String name = resultSet.getString("nom");
                babyNames.add(name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return babyNames;
    }

    public void update(Baby b) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("UPDATE baby SET nom=?, prenom=?, sexe=?, date_naissance=?, poids=?, taille=? WHERE id=?")) {
            preparedStatement.setString(1, b.getNom());
            preparedStatement.setString(2, b.getPrenom());
            preparedStatement.setString(3, b.getSexe());
            preparedStatement.setDate(4, Date.valueOf(b.getDateNaissance()));
            preparedStatement.setDouble(5, b.getPoids());
            preparedStatement.setDouble(6, b.getTaille());
            preparedStatement.setInt(7, b.getId());

            preparedStatement.executeUpdate();
            System.out.println("Baby updated successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public  void delete(Baby b) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("DELETE FROM baby WHERE id=?")) {
            preparedStatement.setInt(1, b.getId());
            preparedStatement.executeUpdate();
            System.out.println("Baby deleted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }





    public List<Baby> search(String name) { // Change parameter type to String
        List<Baby> babyList = new ArrayList<>();
        try {
            String query = "SELECT * FROM baby WHERE nom=?";
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, name); // Set the value of the name parameter
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Baby baby = new Baby();
                baby.setId(rs.getInt("id"));
                baby.setNom(rs.getString("nom"));
                baby.setPrenom(rs.getString("prenom"));
                baby.setSexe(rs.getString("sexe"));
                baby.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                baby.setPoids(rs.getFloat("poids"));
                baby.setTaille(rs.getFloat("taille"));
                babyList.add(baby);
            }
        } catch (SQLException e) {
            System.out.println("Error searching for baby: " + e.getMessage());
        }
        return babyList;
    }
    public double calculateSicknessPercentage() {
        List<Baby> babies = getAll();
        if (babies.isEmpty()) {
            return 0.0; // Return 0 if there are no babies
        }

        int sickCount = 0;
        for (Baby baby : babies) {
            if (baby.isSick()) { // Assuming isSick() returns true if the baby is sick
                sickCount++;
            }
        }

        return ((double) sickCount / babies.size()) * 100; // Calculate the percentage
    }

    public int getBabyIdByName(String babyName) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("SELECT id FROM baby WHERE nom=?")) {
            preparedStatement.setString(1, babyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no baby found with the given name
    }
    public double calculateBoysPercentage() {
        List<Baby> babies = getAll();
        if (babies.isEmpty()) {
            return 0.0; // Return 0 if there are no babies
        }

        int boysCount = 0;
        for (Baby baby : babies) {
            if (baby.getSexe().equals("masculin")) { // Assuming "M" represents boys and "F" represents girls
                boysCount++;
            }
        }

        return ((double) boysCount / babies.size()) * 100; // Calculate the percentage of boys
    }

    public double calculateGirlsPercentage() {
        List<Baby> babies = getAll();
        if (babies.isEmpty()) {
            return 0.0; // Return 0 if there are no babies
        }

        int girlsCount = 0;
        for (Baby baby : babies) {
            if (baby.getSexe().equals("feminine")) { // Assuming "M" represents boys and "F" represents girls
                girlsCount++;
            }
        }

        return ((double) girlsCount / babies.size()) * 100; // Calculate the percentage of girls
    }


}

