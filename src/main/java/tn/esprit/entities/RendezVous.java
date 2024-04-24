package tn.esprit.entities;

import java.time.LocalDate;


public class RendezVous {
    private int id;
    private LocalDate dateReservation;
    private int heure;

    public RendezVous() {
    }

    public RendezVous(LocalDate dateReservation, int heure) {
        this.dateReservation = dateReservation;
        this.heure = heure;
        ;
    }

    public RendezVous(int id, LocalDate dateReservation, int heure) {
        this.id = id;
        this.dateReservation = dateReservation;
        this.heure = heure;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }


}
