package tn.esprit.Controllers;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;

public class UpdatePartenaire {

    @FXML
    private Button BADD;

    @FXML
    private Button BShow;

    @FXML
    private TextField TFDescription;

    @FXML
    private TextField TFEmail;

    @FXML
    private TextField TFMarque;

    @FXML
    private TextField TFNom;

    private Partenaire selectedPartenaire;
    private final PartenaireServices ps = new PartenaireServices();
    public void initData(Partenaire partenaire)
    {
        this.selectedPartenaire = partenaire;
        //Afficher les donnees du post dans les champs corespendantes
        TFNom.setText(partenaire.getNom());
       TFDescription.setText(partenaire.getDescription());
       TFMarque.setText(partenaire.getMarque());



    }



}
