package me.yashtrivedi.ideal_succotash;

import java.util.HashMap;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class User {
    private String name;
    private String email;
    private HashMap<String,Object> timeStampJoined;

    public User() {
    }

    public User(String name, String email, HashMap<String, Object> timeStampJoined) {
        this.name = name;
        this.email = email;
        this.timeStampJoined = timeStampJoined;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, Object> getTimeStampJoined() {
        return timeStampJoined;
    }

    public String getName() {
        return name;
    }
}
