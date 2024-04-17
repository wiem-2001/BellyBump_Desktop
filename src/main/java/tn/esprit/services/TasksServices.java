package tn.esprit.services;

import tn.esprit.entities.Task;
import tn.esprit.entities.User;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TasksServices {
    Connection cnx = MaConnexion.getInstance().getCnx();

    public void add(Task task, int userId) {
        try {
            String query = "INSERT INTO `task` (`idUser`, `description`, `title`, `dateLastModification`) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getTitle());

            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();

            // Convert LocalDateTime to Timestamp
            Timestamp timestamp = Timestamp.valueOf(now);

            statement.setTimestamp(4, timestamp);
            statement.executeUpdate();
            System.out.println("Task added successfully");
        } catch (SQLException e) {
            System.out.println("Error adding task");
            throw new RuntimeException(e);
        }
    }



    public void update(Task task) {
        try {
            String query = "UPDATE `task` SET `description` = ?, `title` = ?, `dateLastModification` = CURRENT_TIMESTAMP WHERE `id` = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, task.getDescription());
            statement.setString(2, task.getTitle());
            statement.setInt(3, task.getId());
            statement.executeUpdate();
            System.out.println("modification task avec success");
        } catch (SQLException e) {
            System.out.println("probleme modification task");
            throw new RuntimeException(e);
        }
    }


    public void delete(Task task) {
        try {
            String query = "DELETE FROM `task` WHERE `id` = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, task.getId());
            statement.executeUpdate();
            System.out.println("suppression task avec success");
        } catch (SQLException e) {
            System.out.println("probleme suppression task");
            throw new RuntimeException(e);
        }
    }

    public List<Task> getAll(String userId) {
        List<Task> tasks = new ArrayList<>();
        try {
            String query = "SELECT * FROM `task` WHERE `idUser` = (SELECT id FROM `user` WHERE `email` = ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("title"),
                        resultSet.getDate("dateLastModification")
                );
                tasks.add(task);
                System.out.println(task.toString());
            }

        } catch (SQLException e) {
            System.out.println("Problème lors de la récupération de la liste des tâches:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return tasks;
    }



    public Task getOne(int id,int idUser) {
        Task task = null;
        try {
            String query = "SELECT * FROM `task` WHERE `idUser` = ? AND `id` = ? ";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, idUser);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("title"),
                        resultSet.getDate("dateLastModification")
                );
            }
            System.out.println("get task avec success");
        } catch (SQLException e) {
            System.out.println("probleme de get task ");
            throw new RuntimeException(e);
        }
        return task;
    }
            }

