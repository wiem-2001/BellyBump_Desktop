package tn.esprit.services;

import tn.esprit.entities.User;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import tn.esprit.entities.User;
import tn.esprit.enums.UserRole;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServices implements IService<User> {
    Connection cnx= MaConnexion.getInstance().getCnx();

    @Override
    public void add(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getOne(int id) {
        return null;
    }


    public User getUser(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Extract user data from the ResultSet and create a User object
                    user = new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("reset_token"),
                            rs.getString("adress"),
                            rs.getString("image"),
                            rs.getInt("status") == 1,
                            rs.getInt("is_verified") == 1,
                            rs.getDate("birthday"),
                            rs.getInt("id"),
                            rs.getInt("phone_number")
                    );

                }
            }
        } catch (SQLException e) {
            System.out.println("error sending request"+e.getMessage());
        }
        System.out.println(user);
        return user;
    }
}
