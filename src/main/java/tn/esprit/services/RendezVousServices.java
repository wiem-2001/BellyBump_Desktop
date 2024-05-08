package tn.esprit.services;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.RendezVous;
import tn.esprit.interfaces.IService;
import tn.esprit.interfaces.IServiceRendezVous;
import tn.esprit.util.MaConnexion;

public class RendezVousServices implements IServiceRendezVous<RendezVous> {
    private Connection connection;

    public RendezVousServices(Connection connection) {
        this.connection = connection;
    }
    private final Connection cnx= MaConnexion.getInstance().getCnx();

    public RendezVousServices() {

    }
//    public boolean isTimeSlotOccupied(LocalDate date, int H) {
//        // Query the database to check if there is any appointment with the selected date
//        boolean isOccupied = false;
//        try {
//            Connection connection = MaConnexion.getInstance().getCnx();
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM rendez_vous WHERE date = ? AND heure = ?");
//            preparedStatement.setDate(1, Date.valueOf(date));
//            preparedStatement.setInt(2, H);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                int count = resultSet.getInt(1);
//                if (count > 0) {
//                    isOccupied = true; // There is already an appointment at the specified date and time
//                }
//            }
//            preparedStatement.close();
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle SQL exception
//        }
//        return isOccupied;
//    }

    public RendezVous getRendezVousByDate(LocalDate date) {
        RendezVous rendezVous = null;
        String query = "SELECT * FROM rendez_vous WHERE date = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int heure = resultSet.getInt("heure");
                rendezVous = new RendezVous(date, heure);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVous;
    }
    public void add(RendezVous rendezVous,String medcinNom) {
        String query = "INSERT INTO rendez_vous (nom_med, date_reservation, heure_reservation) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1,medcinNom);
            stmt.setDate(2, java.sql.Date.valueOf(rendezVous.getDateReservation()));
            stmt.setInt(3,  rendezVous.getHeure());
            stmt.executeUpdate();
            System.out.println("rendezVous ajouteé avec succés");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<RendezVous> getByNomMed(String nomMedcin) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT * FROM rendez_vous WHERE nom_med = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomMedcin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate dateReservation = rs.getDate("date_reservation").toLocalDate();
                int heure = rs.getInt("heure_reservation");
                rendezVousList.add(new RendezVous(id, dateReservation, heure));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    @Override
    public void update(RendezVous rendezVous) {
        String query = "UPDATE rendez_vous SET date_reservation = ?, heure_reservation = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(rendezVous.getDateReservation()));
            stmt.setInt(2, rendezVous.getHeure()); // Utilisez l'heure directement car c'est un entier
            stmt.setInt(3, rendezVous.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(RendezVous rendezVous) {
        String query = "DELETE FROM rendez_vous WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rendezVous.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RendezVous> getAll() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT * FROM rendez_vous";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate dateReservation = rs.getDate("date_reservation").toLocalDate();
                int heure = rs.getInt("heure_reservation"); // Récupérez l'heure comme un int
                String nom_med=rs.getString("nom_med");
                rendezVousList.add(new RendezVous(id, dateReservation, heure,nom_med)); // Créez l'objet RendezVous avec l'heure int
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }
//    public List<RendezVous> getAll() throws SQLException {
//        String req = "SELECT * FROM rendez_vous";
//        Statement ste = cnx.createStatement();
//        ResultSet res = ste.executeQuery(req);
//        List<RendezVous> list = new ArrayList<>();
//        while (res.next()) {
//            RendezVous e = new RendezVous();
//            e.setId(res.getInt(1));
//            e.setHeure(res.getInt("heure_reservation"));
//            // e.setDate_event(res.getString("date_event"));
//            e.setDateReservation(res.getDate("date_reservation").toLocalDate());
//            list.add(e);
//        }
//        return list;
//    }
    @Override
    public RendezVous getOne(int id) {
        String query = "SELECT * FROM rendez_vous WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDate dateReservation = rs.getDate("date_reservation").toLocalDate();
                int heure = rs.getInt("heure_reservation"); // Récupérez l'heure comme un int
                return new RendezVous(id, dateReservation, heure); // Créez l'objet RendezVous avec l'heure int
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}