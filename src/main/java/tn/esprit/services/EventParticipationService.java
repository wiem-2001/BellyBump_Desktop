package tn.esprit.services;

import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.entities.User;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventParticipationService {
    Connection cnx= MaConnexion.getInstance().getCnx();
    //ADD PARTICIPATION TO SET OF EVENTS IN USER & TO SET OF PARTICIPANTS IN EVENT
    public void add(Event event, User user) {
        String req= "INSERT INTO `event_user`(`event_id`, `user_id`) VALUES ('"+event.getId()+"','"+user.getId()+"')";
        try {
            Statement st =cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("participation added successfully!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    // DELETE PARTICIPATION
    public void delete(Event event,User user) {
        String req = "DELETE FROM `event_user` WHERE `event_id` = " + event.getId() + " AND `user_id` = " + user.getId();
        try {
            Statement st = cnx.createStatement();
            int rowsAffected = st.executeUpdate(req);
            System.out.println(rowsAffected + " participation deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // GET ALL PARTICIPATIONS OF  A USER
    public List<Event> getAllParticipatedEvents(User user) {
        List<Event> events = new ArrayList<>();
        String req = "SELECT event_id FROM event_user WHERE user_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                EventService eventService = new EventService();
                Event event = eventService.getOne(eventId);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly instead of just throwing a RuntimeException
        }
        return events;
    }

    // GET ALL PARTICIPATIONS OF AN EVENT
    public List<User> getAllParticipatedUsers(Event event) {
        List<User> users = new ArrayList<>();
        String req = "SELECT user_id FROM event_user WHERE event_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, event.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                UserServices UserServices = new UserServices();
                User user = UserServices.getUser(userId);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly instead of just throwing a RuntimeException
        }
        return users;
    }
}
