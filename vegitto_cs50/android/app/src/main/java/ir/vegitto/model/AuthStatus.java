package ir.vegitto.model;

public class AuthStatus {
    private final boolean status;
    private final String message;

    public AuthStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
