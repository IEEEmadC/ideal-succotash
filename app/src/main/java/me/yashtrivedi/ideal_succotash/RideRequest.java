package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 21/05/16.
 */
public class RideRequest {
    String userName, email;
    int status;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getStatus() {
        return status;
    }
}
