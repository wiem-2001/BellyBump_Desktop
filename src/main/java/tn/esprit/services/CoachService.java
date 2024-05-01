package tn.esprit.services;

import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachService implements IService<Coach> {

    Connection cnx= MaConnexion.getInstance().getCnx();

    @Override
    public void add(Coach coach) {
        String req= "INSERT INTO `coach`(`firstname`, `lastname`, `job`, `phone`, `email`) VALUES ('"+coach.getFirstname()+"','"+coach.getLastname()+"','"+coach.getJob()+"','"+coach.getPhone()+"','"+ coach.getEmail()+"')";
        try {
            Statement st =cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Coach added successfully!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Coach coach) {
        String req = "UPDATE `coach` SET `firstname`='" + coach.getFirstname() + "', `lastname`='" + coach.getLastname() +
                "', `job`='" + coach.getJob() + "', `phone`='" + coach.getPhone() + "', `email`='" + coach.getEmail() +
                "' WHERE `id`=" + coach.getId();
        try {
            Statement st = cnx.createStatement();
            int rowsAffected = st.executeUpdate(req);
            System.out.println(rowsAffected + " rows affected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Coach coach) {


        try {
            String updateQuery = "UPDATE event SET coach_id = NULL WHERE coach_id = ?";
            PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
            updateStatement.setInt(1, coach.getId());
            updateStatement.executeUpdate();
            String req = "DELETE FROM `coach` WHERE `id`=" + coach.getId();
            Statement st = cnx.createStatement();
            int rowsAffected = st.executeUpdate(req);
            System.out.println(rowsAffected + " rows affected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // crud du coach
    @Override
    public List<Coach> getAll() {
        List<Coach> coaches = new ArrayList<>();
        String req= "select * from coach";
        try {
            Statement st=cnx.createStatement();
            ResultSet rs =st.executeQuery(req);
            while (rs.next()) {
                Coach coach = new Coach();
                coach.setId(rs.getInt("id"));
                coach.setFirstname(rs.getString("firstname"));
                coach.setLastname(rs.getString("lastname"));
                coach.setJob(rs.getString("job"));
                coach.setPhone(rs.getInt("phone"));
                coach.setEmail(rs.getString("email"));
                coaches.add(coach);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return coaches;
    }

    @Override
    public Coach getOne(int id) {
        Coach coach = null;
        String req = "SELECT * FROM `coach` WHERE `id`=" + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                coach = new Coach();
                coach.setId(rs.getInt("id"));
                coach.setFirstname(rs.getString("firstname"));
                coach.setLastname(rs.getString("lastname"));
                coach.setJob(rs.getString("job"));
                coach.setPhone(rs.getInt("phone"));
                coach.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coach;
    }

    public Coach getByEmail(String email) {
        Coach coach = null;
        String req = "SELECT * FROM `coach` WHERE `email`='" + email + "'";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                coach = new Coach();
                coach.setId(rs.getInt("id"));
                coach.setFirstname(rs.getString("firstname"));
                coach.setLastname(rs.getString("lastname"));
                coach.setJob(rs.getString("job"));
                coach.setPhone(rs.getInt("phone"));
                coach.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coach;
    }


    public List<Coach> search(String query) {
        List<Coach> filteredCoaches = new ArrayList<>();
        String searchQuery = "SELECT * FROM coach WHERE firstname LIKE '%" + query + "%' OR lastname LIKE '%" + query + "%'";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {
                Coach coach = new Coach();
                coach.setId(rs.getInt("id"));
                coach.setFirstname(rs.getString("firstname"));
                coach.setLastname(rs.getString("lastname"));
                coach.setJob(rs.getString("job"));
                coach.setPhone(rs.getInt("phone"));
                coach.setEmail(rs.getString("email"));

                filteredCoaches.add(coach);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredCoaches;
    }


}
