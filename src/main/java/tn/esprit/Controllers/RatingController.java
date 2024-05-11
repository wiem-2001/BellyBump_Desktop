package tn.esprit.Controllers;
import java.util.ArrayList;
import java.util.List;

public class RatingController {
    private int idEtablissement;
    private int idUser;
    private List<Integer> ratings;

    public RatingController(int idEtablissement, int idUser) {
        this.idEtablissement = idEtablissement;
        this.idUser = idUser;
        this.ratings = new ArrayList<>();
    }

    // Méthode pour ajouter une évaluation
    public void addRating(int rating) {
        ratings.add(rating);
    }

    // Méthode pour calculer la moyenne des évaluations
    public int calculateAverageRating() {
        if (ratings.isEmpty()) {
            return 0; // Retourne 0 si aucune évaluation n'a été ajoutée
        }

        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        // Calcul de la moyenne en arrondissant au nombre entier supérieur
        return Math.round((float) sum / ratings.size());
    }
}
