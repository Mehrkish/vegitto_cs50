package ir.vegitto.model;

public class LogInInputs {
    private String user_input;
    private String password;

    public LogInInputs(String user_input, String password) {
        this.user_input = user_input;
        this.password = password;
    }

    public LogInInputs() {
    }

    public String getUser_input() {
        return user_input;
    }

    public String getPassword() {
        return password;
    }
}
