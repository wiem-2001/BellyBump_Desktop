package tn.esprit.controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    // Vos identifiants Twilio
    public static final String ACCOUNT_SID = "AC2dddcc413aff035374a3bdbf3f4e74ce";
    public static final String AUTH_TOKEN = "ccaefd16c6dbc53eff6f28656d4c4e74";

    // Méthode pour envoyer un SMS
    public static void sendSMS(String recipient, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(recipient),
                        new PhoneNumber("+12073878454"),
                        messageBody)
                .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
}

