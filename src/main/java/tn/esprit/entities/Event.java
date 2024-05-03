package tn.esprit.entities;

import java.sql.Timestamp;
import java.sql.Time;
import java.util.*;

public class Event {

    private int id;
    private String name,image,description,meetingCode;
    private Date day;
    private Time heureDebut,heureFin;
    private boolean launched;
    private int coach;
    private Set<User> participants = new HashSet<>();
    //private ArrayList<User> participants =new ArrayList<>();

    public Event(int id, String name, String image, String description, String meetingCode, Date day, Time heureDebut, Time heureFin, boolean launched, int coach) {
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

    public Event(String name, String image, String description, String meetingCode, Date day, Time heureDebut, Time heureFin, boolean launched, int coach) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.meetingCode = meetingCode;
        this.day = day;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.launched = launched;
        this.coach = coach;
        participants = new HashSet<>();
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

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public int getCoach() {
        return coach;
    }

    public void setCoach(int coach) {
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
