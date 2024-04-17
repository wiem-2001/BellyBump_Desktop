package tn.esprit.entities;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class Produit {

    private int idProd;
    private String nom;
    private String description;
    private Double prix;


    private int quantiteDisponible;
    private byte[] image;

    //private final SimpleIntegerProperty idProd = new SimpleIntegerProperty();
    //private final SimpleStringProperty nom = new SimpleStringProperty();
   // private final SimpleDoubleProperty prix = new SimpleDoubleProperty();
    //private final SimpleObjectProperty<Image> image = new SimpleObjectProperty<>();
    // Constructeur par défaut
    public Produit() {}

    // Constructeur avec paramètres


    public Produit(int idProd, String nom, String description, Double prix,int quantiteDisponible,byte[] image) {
        this.idProd = idProd;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteDisponible=quantiteDisponible;
        this.image= image;
    }

    public Produit(String nom, String description, Double prix,int quantiteDisponible ,byte[] image) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
this.quantiteDisponible=quantiteDisponible;
        this.image= image;
    }

    // Getters et setters
    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
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

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public Image getImageFX() {
        if (this.image != null && this.image.length > 0) {
            return new Image(new ByteArrayInputStream(this.image));
        }
        return null; // ou une image par défaut
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Produit{" +
                "idProd=" + idProd +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
        ", quantiteDisponible=" + quantiteDisponible +
                '}';
    }
}
