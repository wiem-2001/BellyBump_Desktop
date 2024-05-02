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
    public void add(RendezVous rendezVous,String medcinNom) {
        String query = "INSERT INTO rendezvouss (nom_med, date_reservation, heure_reservation) VALUES (?, ?, ?)";
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
        String query = "SELECT * FROM rendezvouss WHERE nom_med = ?";
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
        String query = "UPDATE rendezvouss SET date_reservation = ?, heure_reservation = ? WHERE id = ?";
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
        String query = "DELETE FROM rendezvouss WHERE id = ?";
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
        String query = "SELECT * FROM rendezvouss";
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
        String query = "SELECT * FROM rendezvouss WHERE id = ?";
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