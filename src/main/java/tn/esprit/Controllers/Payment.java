package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Payment{

    @FXML
    private TextField nameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expiryField;
    @FXML
    private TextField cvvField;
    private Cart cartController;
    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private void processPayment() {
        if (!validateInput()) {
            return; // Stop processing if validation fails
        }
        if (cartController != null) {
            cartController.ClearCart();
            // Clear the cart via the cart controller
        }
        // Simulate payment processing
        System.out.println("Processing payment for: " + nameField.getText());
        // Clearing the payment fields
        nameField.clear();
        cardNumberField.clear();
       // expiryField.clear();
        cvvField.clear();



        // Close the payment window or show a confirmation message

        System.out.println("Payment Processed, cart cleared.");
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();

        // Assuming payment is processed successfully

    }

    public void setCartController(Cart  cartController) {
        this.cartController = cartController;
    }





    private boolean validateInput() {
        String cardNumber = cardNumberField.getText();
        String cvv = cvvField.getText();

        // Validate card number
        if (cardNumber.isEmpty() || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            showAlert("Invalid Card Number", "Please enter a valid 16-digit card number.");
            return false;
        }

        // Validate CVV
        if (cvv.isEmpty() || cvv.length() != 3 || !cvv.matches("\\d+")) {
            showAlert("Invalid CVV", "Please enter a valid 3-digit CVV.");
            return false;
        }

        // Validate expiry date using DatePicker
        if (expiryDatePicker.getValue() == null || expiryDatePicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Invalid Expiry Date", "Please select a valid future date.");
            return false;
        }

        return true; // All validations passed
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiryDatePicker.getValue().format(formatter);
    }

}
