package me.yashtrivedi.ideal_succotash.model;

import java.util.Map;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class User {
    private String name;
    private String email;
    private Map<String, String> timeStampJoined;

    public User() {
    }

    public User(String name, String email, Map<String, String> timeStampJoined) {
        this.name = name;
        this.email = email;
        this.timeStampJoined = timeStampJoined;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getTimeStampJoined() {
        return timeStampJoined;
    }

    public String getName() {
        return name;
    }
}
