package tn.esprit;

import tn.esprit.entities.Partenaire;
import tn.esprit.services.PartenaireServices;
import tn.esprit.entities.Produit;
import tn.esprit.services.ProduitServices;
import tn.esprit.util.MaConnexion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //MaConnexion cnx = MaConnexion.getInstance();
        PartenaireServices ps= new PartenaireServices();
        Partenaire partenaire =new Partenaire("momo" , "pampers", "khaledgg@gmail.com","marque de bébé");
        //ps.Insert(partenaire);
        System.out.println(ps.getAll());
        ///p**********************************************************************************our l update
       // Partenaire partenaire = new Partenaire();
        partenaire.setId(1); // The ID of the record you wish to update
        partenaire.setNom("ka");
        partenaire.setMarque("lolo");
        partenaire.setEmail("updated.email@example.com");
        partenaire.setDescription("maaaaaaa");

        ps.update(partenaire);

        //pour supprimerrr*************************************************************
        Partenaire p =new Partenaire();
        p.setNom("ka");
        ps.delete(p);




        //////////POUR PRODUIT
        ProduitServices  pds= new ProduitServices();
       // Produit prod1 = new Produit("perfume","perfums for baby boys",12.2,10);
        //pds.add(prod1);

        System.out.println(pds.getAll());
        Produit produit = new Produit();
        produit.setNom("perfume");
       // pds.augmenterStock(3,2);
       // pds.diminuerStock(3,2);
       //pds.acheterProduitAvecPromotion(3,3);
       // pds.delete(produit);
    }
}