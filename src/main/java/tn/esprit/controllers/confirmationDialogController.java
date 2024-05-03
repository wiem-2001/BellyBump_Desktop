package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Task;
import tn.esprit.entities.User;

public class confirmationDialogController {
    private User user;
    private Task task;
    private boolean confirmed = false;

    @FXML
    private Text dialogDescT,dialogTitleT;

    @FXML
    private Button cancelButton;
    public void initialize() {
        dialogTitleT.setText("Confirmation Dialog");
        dialogDescT.setText("Are you sure you want to save your changes ?");
    }
    @FXML
    void onCancelClicked() {
        closeDialog();
    }
    @FXML
    void onConfirmClicked() {
        confirmed = true;
        closeDialog();
    }
    private void closeDialog() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public boolean isConfirmed() {
        return confirmed;
    }
}
