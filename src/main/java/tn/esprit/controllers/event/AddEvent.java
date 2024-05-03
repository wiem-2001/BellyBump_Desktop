
package tn.esprit.controllers.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddEvent implements Initializable {

    @FXML
    private Text errorTxt;
    @FXML
    private DatePicker dayField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ImageView imageField;

    @FXML
    private TextField meetingCodeField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> starthoursComboBox;

    @FXML
    private ComboBox<String> startminutesComboBox;
    @FXML
    private ComboBox<String> endhoursComboBox;

    @FXML
    private ComboBox<String> endminutesComboBox;

    @FXML
    private ComboBox<String> coachComboBox;

    private final EventService es= new EventService();
    CoachService cs = new CoachService();
    String ImageName="";
    ObservableList<Coach> coachList = FXCollections.observableList(cs.getAll());
    private boolean update;
    private int evnetId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> coachNames = FXCollections.observableArrayList();
        for (Coach coach : coachList) {
            coachNames.add(coach.getFirstname() + " " + coach.getLastname());
        }
        coachComboBox.setItems(coachNames);
//--------------------------------------
        ObservableList<String> hourInt = FXCollections.observableArrayList();
        for(int i=0 ; i<24;i++){
            hourInt.add(String.valueOf(i));
        }
        starthoursComboBox.setItems(hourInt);
        endhoursComboBox.setItems(hourInt);

        ObservableList<String> minuteInt = FXCollections.observableArrayList();
        for(int i=0 ; i<60;i++){
            minuteInt.add(String.valueOf(i));
        }
        startminutesComboBox.setItems(minuteInt);
        endminutesComboBox.setItems(minuteInt);

    }
    @FXML
    void uploadImage(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Generate a unique filename
                String fileExtension = getFileExtension(selectedFile.getName());
                String uniqueFileName = generateUniqueFileName(fileExtension);

                // Specify the upload folder
                //TODO change in every machine
                String uploadFolder = "C:/Users/Eya/Downloads/bellybumpImages/event/";//"C:/Users/user/Downloads/bellybumpImages/event";//
                File destFile = new File(uploadFolder, uniqueFileName);

                // Copy the selected image to the upload folder
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Display the selected image in the ImageView
                Image image = new Image(destFile.toURI().toString());
                imageField.setImage(image);

                // Save the unique filename for later use
                ImageName = uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to upload image: " + e.getMessage());
            }
        } else {
            showAlert("No Image Selected", "Please select an image file.");
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return ""; // No file extension found
    }

    private String generateUniqueFileName(String fileExtension) {
        // Generate a unique filename using UUID
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID + "." + fileExtension;
    }

    @FXML
    void addEvent() {
        // Retrieve values from input fields
        String name = nameField.getText();
        String image = ImageName;
        String description = descriptionField.getText();
        String meetingCode = meetingCodeField.getText();
        Date day = null;

        Time startTime = Time.valueOf("10:00:00") ;
        Time endTime = Time.valueOf("10:00:00")  ;
        if (dayField.getValue() != null){
             day = Date.valueOf(dayField.getValue());
        }
        if (starthoursComboBox.getValue()!= null && startminutesComboBox.getValue()!=null && endhoursComboBox.getValue()!= null && endminutesComboBox.getValue()!=null){
            startTime = Time.valueOf(starthoursComboBox.getValue()+":"+startminutesComboBox.getValue()+":00");
            endTime = Time.valueOf(endhoursComboBox.getValue()+":"+endminutesComboBox.getValue()+":00");
        }
        String selectedCoach = coachComboBox.getValue();

        Coach coach = getCoachByName(selectedCoach);

        LocalDate LocalcurrentDate = LocalDate.now();
        // Convert LocalDate to java.sql.Date
        Date currentDate = Date.valueOf(LocalcurrentDate);

         if (name.isEmpty() || description.isEmpty() || meetingCode.isEmpty() || ImageName.isEmpty() || selectedCoach == null || selectedCoach.isEmpty() || day == null||
                starthoursComboBox.getValue() == null || startminutesComboBox.getValue() == null ||
                endhoursComboBox.getValue() == null || endminutesComboBox.getValue() == null) {
            errorTxt.setText("Fill All the informations");
        } else if (day.before(currentDate)) {
            errorTxt.setText("Select a future date");
        } else if (!TimeValidator(startTime, endTime)) {
            errorTxt.setText("The end time must be after the start time");
        }
        else{
            if (update){
                //update
                Event update_event = new Event(evnetId,name, image, description, meetingCode, day, startTime, endTime, false, coach.getId());
                es.update(update_event);


            }
            else{// Create an instance of Event
                Event new_event = new Event(name, image, description, meetingCode, day, startTime, endTime, false, coach.getId());

                es.add(new_event);
            }
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        }


    }
    void setEventField(int id, String name,String description,String meetingCode,Date day,Time startTime,Time endTime,int coach){

        evnetId=id;

        dayField.setValue(day.toLocalDate());
        descriptionField.setText(description);
        meetingCodeField.setText(meetingCode);
        nameField.setText(name);

        String[] startTokens = startTime.toString().split(":");
        String[] endTokens = endTime.toString().split(":");
        String startHour = startTokens[0];
        String startMinute = startTokens[1];
        String endHour = endTokens[0];
        String endMinute = endTokens[1];

        starthoursComboBox.getSelectionModel().select(startHour);
        startminutesComboBox.getSelectionModel().select(startMinute);
        endhoursComboBox.getSelectionModel().select(endHour);
        endminutesComboBox.getSelectionModel().select(endMinute);
        if (coach != -1){
            Coach c =cs.getOne(coach);
            if (c != null){
                String coachName = c.getFirstname() + " " + c.getLastname();
                coachComboBox.getSelectionModel().select(coachName);
            }

        }


    }
    void setUpdate(boolean b) {
        this.update = b;

    }

    private boolean TimeValidator(Time start,Time end){
        return start.before(end);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    // Méthode pour récupérer le coach correspondant au nom sélectionné dans le ComboBox
    private Coach getCoachByName(String coachName) {
        // Parcourez la liste des coachs pour trouver celui qui correspond au nom sélectionné
        for (Coach coach : coachList) {
            if ((coach.getFirstname() + " " + coach.getLastname()).equals(coachName)) {
                return coach;
            }
        }
        // Si aucun coach correspondant n'est trouvé, retournez null ou gérez l'erreur selon vos besoins
        return null;
    }



}
