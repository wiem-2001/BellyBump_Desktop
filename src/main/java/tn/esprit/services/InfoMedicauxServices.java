package tn.esprit.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.InfoMedicaux;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

public class InfoMedicauxServices implements IService<InfoMedicaux> {


    public InfoMedicauxServices(Connection connection) {}

    public InfoMedicauxServices() {
    }

    @Override
    public void add(InfoMedicaux infoMedicaux) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("INSERT INTO info_medicaux( Baby_name_id, maladie, description, nbr_vaccin, date_vaccin, blood_type, sickness_estimation) VALUES (?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1,infoMedicaux.getBaby_name_id());

            preparedStatement.setString(2, infoMedicaux.getMaladie());
            preparedStatement.setString(3, infoMedicaux.getDescription());
            preparedStatement.setInt(4, infoMedicaux.getNbr_vaccin());
            preparedStatement.setDate(5, infoMedicaux.getDate_vaccin());
            preparedStatement.setString(6, infoMedicaux.getBlood_type());
            preparedStatement.setString(7, infoMedicaux.getSickness_estimation());

            preparedStatement.executeUpdate();
            System.out.println("InfoMedicaux added successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(InfoMedicaux infoMedicaux) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("UPDATE info_medicaux SET baby_name_id=?, maladie=?, description=?, nbr_vaccin=?, date_vaccin=?, blood_type=?, sickness_estimation=? WHERE id=?")) {
            preparedStatement.setInt(1, infoMedicaux.getBaby_name_id());
            preparedStatement.setString(2, infoMedicaux.getMaladie());
            preparedStatement.setString(3, infoMedicaux.getDescription());
            preparedStatement.setInt(4, infoMedicaux.getNbr_vaccin());
            preparedStatement.setDate(5, infoMedicaux.getDate_vaccin());
            preparedStatement.setString(6, infoMedicaux.getBlood_type());
            preparedStatement.setString(7, infoMedicaux.getSickness_estimation());
            preparedStatement.setInt(8, infoMedicaux.getId());

            preparedStatement.executeUpdate();
            System.out.println("InfoMedicaux updated successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(InfoMedicaux infoMedicaux) {
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("DELETE FROM info_medicaux WHERE id=?")) {
            preparedStatement.setInt(1, infoMedicaux.getId());
            preparedStatement.executeUpdate();
            System.out.println("InfoMedicaux deleted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<InfoMedicaux> getByNomBaby(String babyName) {
        List<InfoMedicaux> infoMedicauxList = new ArrayList<>();
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("SELECT * FROM info_medicaux INNER JOIN baby ON info_medicaux.baby_name_id = baby.id WHERE baby.babynom = ?")) {
            preparedStatement.setString(1, babyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InfoMedicaux infoMedicaux = new InfoMedicaux();
                infoMedicaux.setId(resultSet.getInt("id"));
                infoMedicaux.setBaby_name_id(resultSet.getInt("baby_name_id"));
                infoMedicaux.setMaladie(resultSet.getString("maladie"));
                infoMedicaux.setDescription(resultSet.getString("description"));
                infoMedicaux.setNbr_vaccin(resultSet.getInt("nbr_vaccin"));
                infoMedicaux.setDate_vaccin(resultSet.getDate("date_vaccin"));
                infoMedicaux.setBlood_type(resultSet.getString("blood_type"));
                infoMedicaux.setSickness_estimation(resultSet.getString("sickness_estimation"));
                infoMedicauxList.add(infoMedicaux);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return infoMedicauxList;
    }


    @Override
    public List<InfoMedicaux> getAll() {
        List<InfoMedicaux> infoMedicauxList = new ArrayList<>();
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM info_medicaux");
            while (resultSet.next()) {
                InfoMedicaux infoMedicaux = new InfoMedicaux();
                infoMedicaux.setId(resultSet.getInt("id"));
                infoMedicaux.setBaby_name_id(resultSet.getInt("baby_name_id"));
                infoMedicaux.setMaladie(resultSet.getString("maladie"));
                infoMedicaux.setDescription(resultSet.getString("description"));
                infoMedicaux.setNbr_vaccin(resultSet.getInt("nbr_vaccin"));
                infoMedicaux.setDate_vaccin(resultSet.getDate("date_vaccin"));
                infoMedicaux.setBlood_type(resultSet.getString("blood_type"));
                infoMedicaux.setSickness_estimation(resultSet.getString("sickness_estimation"));
                infoMedicauxList.add(infoMedicaux);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return infoMedicauxList;
    }

    @Override
    public InfoMedicaux getOne(int id) {
        InfoMedicaux infoMedicaux = null;
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement("SELECT * FROM info_medicaux WHERE id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                infoMedicaux = new InfoMedicaux();
                infoMedicaux.setId(resultSet.getInt("id"));
                infoMedicaux.setBaby_name_id(resultSet.getInt("baby_name_id"));
                infoMedicaux.setMaladie(resultSet.getString("maladie"));
                infoMedicaux.setDescription(resultSet.getString("description"));
                infoMedicaux.setNbr_vaccin(resultSet.getInt("nbr_vaccin"));
                infoMedicaux.setDate_vaccin(resultSet.getDate("date_vaccin"));
                infoMedicaux.setBlood_type(resultSet.getString("blood_type"));
                infoMedicaux.setSickness_estimation(resultSet.getString("sickness_estimation"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return infoMedicaux;
    }

    @Override
    public InfoMedicaux getOne(String email) {
        return null;
    }

    public int getTotalNumberOfBabies() {
        int totalNumberOfBabies = 0;
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM baby");
            if (resultSet.next()) {
                totalNumberOfBabies = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalNumberOfBabies;
    }

    public int getNumberOfBoys() {
        int numberOfBoys = 0;
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM baby WHERE sexe = 'masculin'");
            if (resultSet.next()) {
                numberOfBoys = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfBoys;
    }

    public int getNumberOfGirls() {
        int numberOfGirls = 0;
        try (Statement statement = MaConnexion.getInstance().getCnx().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM baby WHERE sexe = 'feminine'");
            if (resultSet.next()) {
                numberOfGirls = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfGirls;
    }

}
