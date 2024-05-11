package tn.esprit.Controllers;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Etablissement;

public class EtablissementCell extends ListCell<Etablissement> {
    @Override
    protected void updateItem(Etablissement item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Créez un VBox pour contenir les détails de l'établissement
            VBox vbox = new VBox();
            vbox.setSpacing(5);

            // Ajoutez les détails de l'établissement au VBox
            Label nomLabel = new Label("Nom: " + item.getNom());
            Label typeLabel = new Label("Type: " + item.getType());
            Label localisationLabel = new Label("Localisation: " + item.getLocalisation());

            vbox.getChildren().addAll(nomLabel, typeLabel, localisationLabel);

            // Définissez le VBox comme élément graphique de la cellule
            setGraphic(vbox);
        }
    }
}

