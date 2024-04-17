package tn.esprit.entities;

public class Partenaire{
//ATT

    private int id;
    private String nom;
    private String marque;
    private String email;
    private String description;


    //consttucteurs

    public Partenaire() {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.email = email;
        this.description = description;
    }

    public Partenaire(String nom, String marque, String email, String description) {
        this.nom = nom;
        this.marque = marque;
        this.email = email;
        this.description = description;
    }

//Gettters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //display

    @java.lang.Override
    public java.lang.String toString() {
        return "Partenaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
