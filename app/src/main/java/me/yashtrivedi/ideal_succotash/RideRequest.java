package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 21/05/16.
 */
public class RideRequest {

    String userName, email, area;
    int status;

    public String getArea() {
        return area;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

}
