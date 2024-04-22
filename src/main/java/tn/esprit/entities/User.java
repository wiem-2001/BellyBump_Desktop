package tn.esprit.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {
    private String email,password,first_name,last_name,reset_token,adress,image;
    private boolean status,is_verified;
    private Date birthday;
    private int id,phone_number;
    private Set<Event> events = new HashSet<>();

    public User(String email, String password, String first_name, String last_name, String reset_token, String adress, String image, boolean status, boolean is_verified, Date birthday, int id, int phone_number) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.reset_token = reset_token;
        this.adress = adress;
        this.image = image;
        this.status = status;
        this.is_verified = is_verified;
        this.birthday = birthday;
        this.id = id;
        this.phone_number = phone_number;
    }
    public User(String email, String password, String first_name, String last_name, String reset_token, String adress, String image, boolean status, boolean is_verified, Date birthday, int phone_number) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.reset_token = reset_token;
        this.adress = adress;
        this.image = image;
        this.status = status;
        this.is_verified = is_verified;
        this.birthday = birthday;
        this.phone_number = phone_number;
        events = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", reset_token='" + reset_token + '\'' +
                ", adress='" + adress + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", is_verified=" + is_verified +
                ", birthday=" + birthday +
                ", id=" + id +
                ", phone_number=" + phone_number +
                '}';
    }
}
