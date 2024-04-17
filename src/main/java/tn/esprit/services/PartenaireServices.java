package tn.esprit.services;

import tn.esprit.entities.Partenaire;
import tn.esprit.interfaces.IService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartenaireServices implements IService<Partenaire> {
    //att
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Partenaire partenaire) {

        String req = "INSERT INTO `partenaire`( `nom`, `marque`, `email`, `description`) VALUES ('"+partenaire.getNom()+"','"+partenaire.getMarque()+"','"+partenaire.getEmail()+"','"+partenaire.getDescription()+"')";
       try {
           Statement st = cnx.createStatement();
           st.executeUpdate(req);
           System.out.println("Partenaire added");
       }catch (SQLException e) {
           throw new RuntimeException(e);
       }

       }

    public void Insert(Partenaire partenaire) {

        String req = "INSERT INTO `partenaire`( `nom`, `marque`, `email`, `description`) VALUES  (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            // Set the parameters before calling executeUpdate
            ps.setString(1, partenaire.getNom());
            ps.setString(2, partenaire.getMarque());
            ps.setString(3, partenaire.getEmail());
            ps.setString(4, partenaire.getDescription());
            // Now, execute the update with the parameters set
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Partenaire partenaire) {

        String req = "UPDATE `partenaire` SET `nom` = ?, `marque` = ?, `email` = ?, `description` = ? WHERE `id` = ?";

        try {
            // Prepare the SQL statement with the database connection object `cnx`.
            PreparedStatement ps = cnx.prepareStatement(req);

            // Set the parameters for the update with the values from the 'partenaire' object.
            ps.setString(1, partenaire.getNom());
            ps.setString(2, partenaire.getMarque());
            ps.setString(3, partenaire.getEmail());
            ps.setString(4, partenaire.getDescription());

            ps.setInt(5, partenaire.getId());

            // Execute the update. This method returns the number of rows that were updated.
            int rowsAffected = ps.executeUpdate();

            // Optional: You can do something with the rowsAffected if needed.
            if (rowsAffected > 0) {
                System.out.println("Update successful. " + rowsAffected + " row(s) updated.");
            } else {
                System.out.println("No rows were updated. Check the partenaire ID.");
            }

        } catch (SQLException e) {
            // Handle any SQL errors that occur during the update.
            throw new RuntimeException("Error updating partenaire: " + e.getMessage(), e);
        }
    }



    @Override
    public void delete(Partenaire partenaire) {

        String requete = "DELETE FROM partenaire WHERE nom = ?";

        try {
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, partenaire.getNom()); // Set the nom of the partenaire to delete
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Partenaire supprimé avec succès!");
            } else {
                System.out.println("Aucun partenaire trouvé avec ce nom pour la suppression.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du partenaire: " + e.getMessage());
            // Handle exception appropriately
        }
    }


    @Override
    public List<Partenaire> getAll() {
      List<Partenaire> partenaires = new ArrayList<>();
      String req = "Select * from partenaire";
              try{
                  Statement st =cnx.createStatement();
                  ResultSet res = st.executeQuery(req);
                  while(res.next())
                  {Partenaire partenaire =new Partenaire();
                      partenaire.setId(res.getInt("Id"));
                      partenaire.setNom(res.getString(2));
                      partenaire.setMarque(res.getString(3));
                      partenaire.setEmail(res.getString(4));
                      partenaire.setDescription(res.getString(5));
                      partenaires.add(partenaire);

                  }

              }catch(SQLException e){
                  throw new RuntimeException(e);
              }
              return partenaires;

    }

    @Override
    public Partenaire getOne(int id) {
        return null;
    }
}
