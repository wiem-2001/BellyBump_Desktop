package tn.esprit.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SMSSender {
    public static final String ACCOUNT_SID = "AC89f2b1faa70a3c1b8298ca036be10e89";
    public static final String AUTH_TOKEN = "de1c8d0a2253f8c673e9ebbd0e8b85df";

    public void sendSMS(String phoneNumber, String verificationCode) {
        // Initialize Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send SMS message
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber("+21625091434"),
                        "Your verification code is: " + verificationCode).create();

        System.out.println("SMS sent successfully. Message SID: " + message.getSid());
    }
}
