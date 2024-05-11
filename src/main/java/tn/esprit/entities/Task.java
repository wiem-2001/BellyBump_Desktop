package tn.esprit.entities;

import java.util.Date;

public class Task {
    private int id;
    private int idUser;
    private String description;
    private String title;
    private Date dateLastModification;
    private String Tag;

    public Task(int id, int idUser, String description, String title,String tag, Date dateLastModification) {
        this.id = id;
        this.idUser = idUser;
        this.description = description;
        this.title = title;
        this.dateLastModification = dateLastModification;
        Tag = tag;
    }

    public Task(int id, String description, String title, String tag) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.Tag = tag;
    }
    public Task(int id, String description, String title,String tag ,java.sql.Date dateLastModification) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.Tag = tag;
        this.dateLastModification=dateLastModification;
    }
    public Task() {
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", dateLastModification=" + dateLastModification +
                ", Tag='" + Tag + '\'' +
                '}';
    }
}
