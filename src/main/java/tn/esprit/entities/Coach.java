package tn.esprit.entities;

public class Coach {

    private int id,phone;
    private String firstname, lastname, job ,email;

    public Coach(int id, int phone, String firstname, String lastname, String job, String email) {
        this.id = id;
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.email = email;
    }

    public Coach(int phone, String firstname, String lastname, String job, String email) {
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.email = email;
    }
    public Coach(){}
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", phone=" + phone +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", job='" + job + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
