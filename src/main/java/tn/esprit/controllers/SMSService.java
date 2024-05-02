package tn.esprit.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC14b803fc12f81218db9aef9ffbce0d06";
    public static final String AUTH_TOKEN = "9749633f59d550fcd6b9904ba2acd709";

    // Méthode pour envoyer un SMS
    public static void sendSMS(String recipient, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(recipient),
                        new PhoneNumber("+201656880822"),
                        messageBody)
                .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
}

