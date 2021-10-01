package ir.vegitto.model;

import com.google.gson.annotations.SerializedName;

public class SignUpInputs {
    private String username;
    private String user_input;
    @SerializedName("confirm_password")
    private String confirmPassword;
    private String password;

    public SignUpInputs(String username, String user_input, String password, String confirmPassword) {
        this.username = username;
        this.user_input = user_input;
        this.confirmPassword = confirmPassword;
        this.password = password;
    }

    public SignUpInputs() {
    }

    public String getUsername() {
        return username;
    }

    public String getUser_input() {
        return user_input;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
