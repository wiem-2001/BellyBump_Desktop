package tn.esprit.entities;

import tn.esprit.enums.UserRole;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String email,password,first_name,last_name,reset_token,adress,image;
    private int status,is_verified;
    private Date birthday;
    private int id,phone_number;
    private UserRole role;
    private String verificationCode;

    private Timestamp createdAt; // New attribute
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    //used to get user with all attributs
    public User(String email, String password, String first_name, String last_name, String reset_token, String adress, String image, int status, int is_verified, Date birthday, int id, int phone_number,String verificationCode) {
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
        this.verificationCode=verificationCode;
    }
    //used to add user
    public User(String email, String password, String first_name, String last_name, String adress, Date birthday, int phone_number) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.adress = adress;
        this.birthday = birthday;
        this.phone_number = phone_number;
    }
    //used to get the current user
    public User(String email,String first_name, String last_name, String adress, Date birthday, int phone_number) {
        this.email=email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.adress = adress;
        this.birthday = birthday;
        this.phone_number = phone_number;
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


    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;

    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User() {

    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", this.first_name);
        map.put("lastName", this.last_name);
        map.put("email", this.email);
        return map;
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
                ", role=" + role +
                ", verificationCode=" + verificationCode +
                '}';
    }
}