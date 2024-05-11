package tn.esprit.entities;

import java.sql.Date;

public class InfoMedicaux {
    public int id;

    private String babyName;
    public String maladie;
    public String description;
    public int nbr_vaccin;
    public Date date_vaccin;
    public String blood_type;
    public String sickness_estimation;
    private int baby_name_id;

    public InfoMedicaux (){

    }
    public InfoMedicaux( int baby_name_id, String maladie, String description, int nbr_vaccin, Date date_vaccin, String blood_type, String sickness_estimation) {

        this.baby_name_id = baby_name_id;
        this.maladie = maladie;
        this.description = description;
        this.nbr_vaccin = nbr_vaccin;
        this.date_vaccin = date_vaccin;
        this.blood_type = blood_type;
        this.sickness_estimation = sickness_estimation;
    }
    public InfoMedicaux( String maladie, String description, int nbr_vaccin, Date date_vaccin, String blood_type, String sickness_estimation) {

        this.maladie = maladie;
        this.description = description;
        this.nbr_vaccin = nbr_vaccin;
        this.date_vaccin = date_vaccin;
        this.blood_type = blood_type;
        this.sickness_estimation = sickness_estimation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaby_name_id() {
        return baby_name_id;
    }

    public void setBaby_name_id(int baby_name_id) {
        this.baby_name_id = baby_name_id;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbr_vaccin() {
        return nbr_vaccin;
    }

    public void setNbr_vaccin(int nbr_vaccin) {
        this.nbr_vaccin = nbr_vaccin;
    }

    public Date getDate_vaccin() {
        return date_vaccin;
    }

    public void setDate_vaccin(Date date_vaccin) {
        this.date_vaccin = date_vaccin;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getSickness_estimation() {
        return sickness_estimation;
    }

    public void setSickness_estimation(String sickness_estimation) {
        this.sickness_estimation = sickness_estimation;
    }

    @Override
    public String toString() {
        return "InfoMedicaux{" +
                "id=" + id +
                ", maladie='" + maladie + '\'' +
                ", description='" + description + '\'' +
                ", nbr_vaccin=" + nbr_vaccin +
                ", date_vaccin=" + date_vaccin +
                ", blood_type='" + blood_type + '\'' +
                ", sickness_estimation='" + sickness_estimation + '\'' +
                '}';
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyNameId(String babyNameId) {
        this.babyName = babyNameId;
    }
}

