package tn.esprit.services;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User;
import tn.esprit.enums.UserRole;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServices implements IService<User> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public void add(User user) {
        String req = "INSERT INTO `user` (`email`,`roles`, `password`, `is_verified`, `birthday`, `first_name`, `last_name`, `adress`, `phone_number`, `image`, `status`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ps.setString(2, UserRole.ROLE_MOTHER.getRoleName());
            String hashedPassword = hashPassword(user.getPassword());
            ps.setString(3, hashedPassword);
            ps.setInt(4, 0);
            ps.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
            ps.setString(6, user.getFirst_name());
            ps.setString(7, user.getLast_name());
            ps.setString(8, user.getAdress());
            ps.setInt(9, user.getPhone_number());
            ps.setString(10, user.getImage());
            ps.setInt(11, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User user) {
        String query = "UPDATE user SET last_name = ?, first_name = ?, adress = ?, birthday = ? , phone_number = ? WHERE email = ?";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setString(1, user.getLast_name());
            pst.setString(2, user.getFirst_name());
            pst.setString(3, user.getAdress());
            pst.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
            pst.setInt(5, user.getPhone_number());
            pst.setString(6, user.getEmail());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with email: " + user.getEmail());
            } else {
                System.out.println("User updated successfully!");
            }
        }
        catch (SQLException e) {
            System.out.println("error sending request"+e.getMessage());
        }
    }
    public void updatePassword(String email, String newPassword) {
        String hashedPassword = hashPassword(newPassword); // Hash the new password
        String query = "UPDATE `user` SET `password` = ? WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with email: " + email);
            } else {
                System.out.println("Password updated successfully for user with email: " + email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM `user` where`user`.`email`=?;";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setString(1,user.getEmail());
            int n=pst.executeUpdate();
            if (n>=1)
                System.out.println("suppression réussie");
        }catch(SQLException ex)
        {
            System.out.println("error sending request"+ex.getMessage());
        }
    }
    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM `user`";
        try{
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    // Populate user object with data from the result set
                    user.setEmail(rs.getString("email"));
                    // Populate other user attributes similarly
                    userList.add(user);}

            } catch (SQLException e) {
                // Handle database errors
                e.printStackTrace();
            }
        for (User user : userList) {
            System.out.println("User Email: " + user.getEmail());
        }
            return userList;
    }

    @Override
    public User getOne(String email) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
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
                            rs.getInt("status"),
                            rs.getInt("is_verified"),
                            rs.getDate("birthday"),
                            rs.getInt("id"),
                            rs.getInt("phone_number")
                    );
                    UserRole role = UserRole.valueOf(rs.getString("roles"));
                    user.setRole(role);
                }
            }
        } catch (SQLException e) {
            System.out.println("error sending request"+e.getMessage());
        }
        System.out.println(user);
        return user;
    }
}

