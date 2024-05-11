package tn.esprit.util;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResetToken {
    private String token;
    private LocalDateTime expiryDateTime;

    public ResetToken(String token, LocalDateTime expiryDateTime) {
        this.token = token;
        this.expiryDateTime = expiryDateTime;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }
}
