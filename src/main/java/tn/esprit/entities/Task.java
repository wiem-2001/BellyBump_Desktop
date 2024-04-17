package tn.esprit.entities;

import java.util.Date;

public class Task {
    private int id;
    private int idUser;
    private String description;
    private String title;
    private Date dateLastModification;

    public Task(int id, String description, String title, Date dateLastModification) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.dateLastModification = dateLastModification;
    }
    public Task(int id, String description, String title) {
        this.id = id;
        this.description = description;
        this.title = title;
    }
    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", dateLastModification=" + dateLastModification +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateLastModification() {
        return dateLastModification;
    }

    public void setDateLastModification(Date dateLastModification) {
        this.dateLastModification = dateLastModification;
    }
}
