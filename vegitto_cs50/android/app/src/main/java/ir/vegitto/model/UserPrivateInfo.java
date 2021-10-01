package ir.vegitto.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserPrivateInfo implements Serializable {
    private final String first_name;
    private final String last_name;
    private final String gender;
    @SerializedName("type")
    private final String role;
    private final String mobile_number;
    private final String email;
    private final String username;
    private List<Integer> groups;

    public UserPrivateInfo(String first_name, String last_name, String gender, String role, String mobile_number, String email, String username) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.role = role;
        this.mobile_number = mobile_number;
        this.email = email;
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public List<Integer> getGroups() {
        return groups;
    }
}
