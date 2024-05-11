package tn.esprit.Controllers.coach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Coach;
import tn.esprit.services.CoachService;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddCoach  implements Initializable {
    @FXML
    private Text errorTxt;
    @FXML
    private Button Savebtn;

    @FXML
    private Button cleanbtn;

    @FXML
    private TextField emailFld;


    @FXML
    private TextField jobFld;

    @FXML
    private TextField fnameFld;
    @FXML
    private TextField lnameFld;

    @FXML
    private TextField phoneFld;

    CoachService cs = new CoachService();

    ObservableList<Coach> observableList = FXCollections.observableArrayList();

    Coach coach=null;
    private boolean update;
    int coachId ;

    @FXML
    void clean() {
        fnameFld.setText(null);
        lnameFld.setText(null);
        jobFld.setText(null);
        phoneFld.setText(null);
        emailFld.setText(null);
    }

    @FXML
    void save() {
        String fname = fnameFld.getText();
        String lname= lnameFld.getText();
        String job = jobFld.getText();
        String phone = phoneFld.getText();
        String email = emailFld.getText();
        System.out.println("coach"+ fname+ lname +job+phone+email);


        if (fname.isEmpty() || lname.isEmpty() || job.isEmpty()|| phone.isEmpty() || email.isEmpty()){
            errorTxt.setText("Fill All the informations");
        }
        else if (!isValidEmail(email)){errorTxt.setText("Enter a valid email address");}
        else if (!isUniqueEmail(email,coachId)){errorTxt.setText("Email already used");}
        else if (!isValidPhoneNumber(phone)){errorTxt.setText("Enter a valid phone number");}
        else{
            if (update){
                cs.update(new Coach(coachId,Integer.parseInt(phone),fname,lname,job,email));
            }
            else{
                cs.add(new Coach(Integer.parseInt(phone),fname,lname,job,email));
            }

            Stage stage = (Stage) fnameFld.getScene().getWindow();
            stage.close();
        }

    }

    //validation de l'unicité de l'email
    private boolean isUniqueEmail(String email,int id){
        Coach coach_email= cs.getByEmail(email,id);
        return coach_email==null ;

    }
    // Validation de format de l'email
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier le format d'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Méthode pour valider un numéro de téléphone (8 chiffres)
    private boolean isValidPhoneNumber(String phone) {
        return phone.length() == 8 && phone.matches("[0-9]+");
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void setTextField(int id, String fname, String lname, String job, int phone ,String email){
        coachId=id;
        fnameFld.setText(fname);
        lnameFld.setText(lname);
        jobFld.setText(job);
        phoneFld.setText(String.valueOf(phone));
        emailFld.setText(email);
    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
