package tn.esprit.entities;

import javafx.scene.control.Button;

public class Etablissement {
    private int id;
    private String nom;
    private String type;
    private String localisation;
    private Button btnModifier;
    private Button btnSupprimer;

    public Etablissement(int id, String nom, String type, String localisation, Button btnModifier, Button btnSupprimer) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.localisation = localisation;
        this.btnModifier = btnModifier;
        this.btnSupprimer = btnSupprimer;
    }

    public Etablissement(int id, String nom) {

    }

    public Button getBtnModifier() {
        return btnModifier;
    }

    public void setBtnModifier(Button btnModifier) {
        this.btnModifier = btnModifier;
    }

    public Button getBtnSupprimer() {
        return btnSupprimer;
    }

    public void setBtnSupprimer(Button btnSupprimer) {
        this.btnSupprimer = btnSupprimer;
    }

    public Etablissement(int id, String nom, String type, String localisation) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.localisation = localisation;
    }

    public Etablissement(String nom, String type, String localisation) {
        this.nom = nom;
        this.type = type;
        this.localisation = localisation;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
