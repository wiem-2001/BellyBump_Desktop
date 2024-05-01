package tn.esprit.services;

import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.sql.Date;
import java.sql.Time;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventService implements IService<Event> {

    Connection cnx= MaConnexion.getInstance().getCnx();
    // crud du event
    @Override
    public void add(Event event) {
            int coachId =   event.getCoach();
            String req = "INSERT INTO `event`(`name`, `image`, `description`, `day`, `heure_debut`, `heure_fin`, `meeting_code`, `coach_id`, `launched`) " +
                    "VALUES ('"+event.getName()+"','"+event.getImage()+"','"+event.getDescription()+"','"+event.getDay()+"','"+ event.getHeureDebut()+"'," +
                    "'"+event.getHeureFin()+"','"+event.getMeetingCode()+"','"+coachId+"','"+(event.isLaunched() ? 1 : 0)+"')";
        try {
            Statement st =cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Event added successfully!");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(Event event) {
        String req = "UPDATE `event` SET `name`='"+event.getName()+"',`image`='"+event.getImage()+"'," +
                "`description`='"+event.getDescription()+"',`day`='"+event.getDay()+"',`heure_debut`='"+event.getHeureDebut()+"'," +
                "`heure_fin`='"+event.getHeureFin()+"',`meeting_code`='"+event.getMeetingCode()+"'," +
                "`coach_id`='"+event.getCoach()+"',`launched`= '"+(event.isLaunched() ? 1 : 0)+"' WHERE `id`=" + event.getId();

        try {
            Statement st = cnx.createStatement();
            int rowsAffected = st.executeUpdate(req);
            System.out.println(rowsAffected + " rows affected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Event event) {
        String req = "DELETE FROM `event` WHERE `id`=" + event.getId();
        try {
            Statement st = cnx.createStatement();
            int rowsAffected = st.executeUpdate(req);
            System.out.println(rowsAffected + " rows affected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> getAll() {
        CoachService cs = new CoachService();
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM `event`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setImage(rs.getString("image"));
                event.setDescription(rs.getString("description"));
                event.setDay(rs.getDate("day"));

                event.setHeureDebut(rs.getTime("heure_debut"));
                event.setHeureFin(rs.getTime("heure_fin"));
                event.setCoach(rs.getInt("coach_id"));

                event.setMeetingCode(rs.getString("meeting_code"));
                event.setLaunched(rs.getInt("launched") == 1); // Convert integer to boolean
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
    public List<Event> getAllFutureEvents() {
        CoachService cs = new CoachService();
        List<Event> futureEvents = new ArrayList<>();
        String req = "SELECT * FROM `event` WHERE `day` > CURDATE()";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setImage(rs.getString("image"));
                event.setDescription(rs.getString("description"));
                event.setDay(rs.getDate("day"));

                event.setHeureDebut(rs.getTime("heure_debut"));
                event.setHeureFin(rs.getTime("heure_fin"));
                event.setCoach(rs.getInt("coach_id"));

                event.setMeetingCode(rs.getString("meeting_code"));
                event.setLaunched(rs.getInt("launched") == 1); // Convert integer to boolean
                futureEvents.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return futureEvents;
    }


    @Override
    public Event getOne(int id) {
        Event event = null;
        String req = "SELECT * FROM `event` WHERE `id` = " + id;
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setImage(rs.getString("image"));
                event.setDescription(rs.getString("description"));
                event.setDay(rs.getDate("day"));

                event.setHeureDebut(rs.getTime("heure_debut"));
                event.setHeureFin(rs.getTime("heure_fin"));
                event.setCoach(rs.getInt("coach_id"));
                event.setMeetingCode(rs.getString("meeting_code"));
                // Set Coach object
                // event.setCoach(coach); // Assuming you have a method to set coach
                event.setLaunched(rs.getInt("launched") == 1); // Convert integer to boolean
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }

    public List<Event> search(String query){
        List<Event> filteredEvents = new ArrayList<>();
        String searchQuery = "SELECT * FROM event WHERE name LIKE '%" + query + "%' OR description LIKE '%" + query + "%'";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setImage(rs.getString("image"));
                event.setDescription(rs.getString("description"));
                event.setDay(rs.getDate("day"));

                event.setHeureDebut(rs.getTime("heure_debut"));
                event.setHeureFin(rs.getTime("heure_fin"));
                event.setCoach(rs.getInt("coach_id"));
                event.setMeetingCode(rs.getString("meeting_code"));

                event.setLaunched(rs.getInt("launched") == 1); // Convert integer to boolean
                filteredEvents.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return filteredEvents;
    }

}
