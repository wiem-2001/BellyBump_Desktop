package tn.esprit.services;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User;
import tn.esprit.entities.UserStatistics;
import tn.esprit.enums.UserRole;
import tn.esprit.util.EmailContentBuilder;
import tn.esprit.interfaces.IService;
import tn.esprit.util.EmailSender;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices implements IService<User> {

    Connection cnx = MaConnexion.getInstance().getCnx();

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isValidEmail(String email) {
        // Expression régulière pour valider l'adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Création du pattern avec l'expression régulière
        Pattern pattern = Pattern.compile(regex);

        // Création du matcher pour l'adresse e-mail fournie
        Matcher matcher = pattern.matcher(email);

        // Retourne true si l'adresse e-mail correspond au format, sinon false
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(int phoneNumber) {
        // Vérification que la chaîne ne soit pas vide et qu'elle contienne uniquement des chiffres
        String phoneNumberStr = String.valueOf(phoneNumber);
        return phoneNumberStr != null && phoneNumberStr.length() == 8;
    }



    @Override
    public void add(User user) {
        String req = "INSERT INTO `user` (`email`,`roles`, `password`, `is_verified`, `birthday`, `first_name`, `last_name`, `adress`, `phone_number`, `image`, `status`, `created_at`) VALUES (?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
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
            ps.setString(10, "userProfilImage.png");
            ps.setInt(11, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE user SET last_name = ?, first_name = ?, adress = ?, birthday = ? , phone_number = ? ,image= ? WHERE email = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, user.getLast_name());
            pst.setString(2, user.getFirst_name());
            pst.setString(3, user.getAdress());
            pst.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
            pst.setInt(5, user.getPhone_number());
            pst.setString(6, user.getImage());
            pst.setString(7, user.getEmail());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with email: " + user.getEmail());
            } else {
                System.out.println("User updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("error sending request" + e.getMessage());
        }
    }
    public void updateProfilImage(String imagePath,String email) {
        String query = "UPDATE user SET image = ? WHERE email = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, imagePath);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with email: " + email);
            } else {
                System.out.println("User image updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user image: " + e.getMessage());
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
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, user.getEmail());
            int n = pst.executeUpdate();

            if (n >= 1) {
                EmailSender.sendEmail(user.getEmail(), "dellete ", EmailContentBuilder.buildDeleteNotificationEmail(user));
                System.out.println("suppression réussie");
            }
        } catch (SQLException ex) {
            System.out.println("error sending request" + ex.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM `user` WHERE `user`.`roles` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            // Set the role parameter to UserRole.ROLE_MOTHER.getRoleName()
            ps.setString(1, UserRole.ROLE_MOTHER.getRoleName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                // Populate user object with data from the result set
                user.setEmail(rs.getString("email"));
                user.setLast_name(rs.getString("last_name"));
                user.setFirst_name(rs.getString("first_name"));
                user.setBirthday(rs.getDate("birthday"));
                user.setStatus((rs.getInt("status")));
                userList.add(user);
            }
            // Close ResultSet, PreparedStatement, and database connection
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // Handle database errors
            System.out.println("error sending request" + e.getMessage());
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
            System.out.println("error sending request" + e.getMessage());
        }
        System.out.println(user);
        return user;
    }

    public void updateStatus(User user) {
        if (user.getStatus() == 1) {
            String query = "UPDATE user SET status = 0 WHERE email = ?";
            try {
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setString(1, user.getEmail());
                int n = pst.executeUpdate();
                if (n >= 1) {
                    EmailSender.sendEmail(user.getEmail(), "account status ", EmailContentBuilder.buildAccountChangeNotificationEmail(user,user.getStatus()));
                    System.out.println("user account desactivated");
                }
            } catch (SQLException e) {
                System.out.println("error sending request" + e.getMessage());
            }
        } else{
            String query = "UPDATE user SET status = 1 WHERE email = ?";
            try {
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setString(1, user.getEmail());
                int n = pst.executeUpdate();
                if (n >= 1) {
                    EmailSender.sendEmail(user.getEmail(), "account status ", EmailContentBuilder.buildAccountChangeNotificationEmail(user,user.getStatus()));
                    System.out.println("user account activated");
                }
            } catch (SQLException e) {
                System.out.println("error sending request" + e.getMessage());
            }
        }

    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    public String login(String email, String password) {
        String query = "SELECT * FROM `user` WHERE `email` = ? ";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, email);
          //  pst.setString(2, hashPassword(password)); // Assuming password is hashed in the database
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // User found, return the user retrieved from the database
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setBirthday(rs.getDate("birthday"));
                user.setAdress(rs.getString("adress"));
                user.setPhone_number(rs.getInt("phone_number"));
                user.setIs_verified(rs.getInt("is_verified"));
                user.setStatus(rs.getInt("status"));
                user.setPassword(rs.getString("password"));
                String role=rs.getString("roles");
                if(role.equals("ROLE_Mother"))
                {
                    user.setRole(UserRole.ROLE_MOTHER);
                }
                else {
                    user.setRole(UserRole.ROLE_ADMIN);
                }
                if(!verifyPassword(password,user.getPassword())){
                    System.out.println("username or password incorrect");
                    return ("incorrectCredentiels");
                }
                System.out.println("user logged in");
                return ("true");
            } else {
                System.out.println("user not found");
                return ("userNotFound");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during login", e);
        }
    }

    public UserStatistics getUserStatisticsByStatus() {
        String activeUsersQuery = "SELECT COUNT(*) FROM `user` WHERE `status` = 1 AND `roles`='ROLE_MOTHER'";
        String inactiveUsersQuery = "SELECT COUNT(*) FROM `user` WHERE `status` = 0 AND `roles`='ROLE_MOTHER'";

        try {
            PreparedStatement activeUsersStatement = cnx.prepareStatement(activeUsersQuery);
            PreparedStatement inactiveUsersStatement = cnx.prepareStatement(inactiveUsersQuery);

            ResultSet activeUsersResult = activeUsersStatement.executeQuery();
            ResultSet inactiveUsersResult = inactiveUsersStatement.executeQuery();

            int activeUsersCount = 0;
            int inactiveUsersCount = 0;

            if (activeUsersResult.next()) {
                activeUsersCount = activeUsersResult.getInt(1);
            }

            if (inactiveUsersResult.next()) {
                inactiveUsersCount = inactiveUsersResult.getInt(1);
            }
            System.out.println("active users number : "+activeUsersCount);
            System.out.println("inactive users number : "+inactiveUsersCount);

            return new UserStatistics(activeUsersCount, inactiveUsersCount);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des statistiques utilisateur", e);
        }
    }
    public Map<Integer, Integer> getUsersCreatedPerMonth() {
        String usersPerMonthQuery = "SELECT MONTH(created_at), COUNT(*) FROM `user` WHERE YEAR(created_at) = YEAR(CURRENT_DATE) AND `roles`='ROLE_MOTHER' GROUP BY MONTH(created_at)";
        Map<Integer, Integer> usersPerMonth = new HashMap<>();

        try {
            PreparedStatement statement = cnx.prepareStatement(usersPerMonthQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int month = resultSet.getInt(1);
                int userCount = resultSet.getInt(2);
                usersPerMonth.put(month, userCount);
                System.out.println("month : "+month+'\n'+"number of created users : "+userCount);
            }
            return usersPerMonth;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users created per month", e);
        }
    }
    public boolean userExists(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a user with the email exists, false otherwise
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return false; // Error occurred, return false
        }
    }
    public String getPasswordByEmail(String email) {
        String password = null;
        String query = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    password = rs.getString("password");
                } else {
                    // Handle case where email doesn't exist
                    System.out.println("Email not found: " + email);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving password: " + e.getMessage());
        }
        return password;
    }
public void saveResetToken(String email,String token){
    String query = "UPDATE `user` SET `reset_token` = ? WHERE `email` = ?";
    try {
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, token);
        ps.setString(2, email);
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated == 0) {
            System.out.println("No user found with email: " + email);
        } else {
            System.out.println("token saved: " + email);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}



























































































































