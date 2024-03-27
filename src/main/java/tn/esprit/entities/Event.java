package tn.esprit.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Event {

    private int id;
    private String name,image,description,meetingCode;
    private Date day;
    private LocalDateTime heureDebut,heureFin;
    private boolean launched;
    private Coach coach;

    public Event(int id, String name, String image, String description, String meetingCode, Date day, LocalDateTime heureDebut, LocalDateTime heureFin, boolean launched, Coach coach) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.meetingCode = meetingCode;
        this.day = day;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.launched = launched;
        this.coach = coach;
    }

    public Event(String name, String image, String description, String meetingCode, Date day, LocalDateTime heureDebut, LocalDateTime heureFin, boolean launched, Coach coach) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.meetingCode = meetingCode;
        this.day = day;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.launched = launched;
        this.coach = coach;
    }
    public Event(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalDateTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalDateTime heureFin) {
        this.heureFin = heureFin;
    }

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", meetingCode='" + meetingCode + '\'' +
                ", day=" + day +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", launched=" + launched +
                ", coach=" + coach +
                '}';
    }
}
