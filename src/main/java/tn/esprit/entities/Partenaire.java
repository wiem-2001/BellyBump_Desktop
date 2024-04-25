package tn.esprit.entities;

import java.util.ArrayList;
import java.util.List;

public class Partenaire {

    private int id;
    private String nom;
    private String marque; // Attribut ajouté pour correspondre à la base de données
    private String email;
    private String description;
    private List<Produit> produits; // Liste pour garder la trace des produits associés

    // Constructeurs
    public Partenaire() {
        produits = new ArrayList<>();
    }

    public Partenaire(int id, String nom, String marque, String email, String description) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.email = email;
        this.description = description;
        produits = new ArrayList<>();
    }

    public Partenaire(String nom, String marque, String email, String description) {
        this.nom = nom;
        this.marque = marque;
        this.email = email;
        this.description = description;
        produits = new ArrayList<>();
    }

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

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    // Méthode pour ajouter un produit à ce partenaire
    public void addProduit(Produit produit) {
        produits.add(produit);
        produit.setPartenaireId(this.id);
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Partenaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                // ", produits=" + produits +  // Ne pas inclure produits dans toString pour éviter la récursivité
                '}';
    }
}
