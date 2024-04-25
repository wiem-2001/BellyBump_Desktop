package tn.esprit.entities;

import java.time.LocalDateTime;

public class Produit {

    private int id;
    private String nom;
    private String description;
    private Double prix;

    private String imagePath; // J'ai changé le type pour correspondre à un chemin d'accès ou URL stocké en tant que VARCHAR
    private int partenaire_id; // Clé étrangère pour l'association avec Partenaire

    // Constructeurs
    public Produit() {}

    public Produit(int id, String nom, String description, Double prix, String imagePath, int partenaire_id) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;

        this.imagePath = imagePath;
        this.partenaire_id = partenaire_id;
    }

    public Produit(String nom, String description, Double prix, String imagePath, int partenaire_id) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;

        this.imagePath = imagePath;
        this.partenaire_id = partenaire_id;
    }

    // Getters et setters
    // ... Inclure tous les getters et setters ici ...

    // Getters et setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }



    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPartenaireId() {
        return partenaire_id;
    }

    public void setPartenaireId(int partenaire_id) {
        this.partenaire_id = partenaire_id;
    }

    // toString
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", imagePath='" + imagePath + '\'' +
                ", partenaire_id=" + partenaire_id +
                '}';
    }
}
