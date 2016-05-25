package me.yashtrivedi.ideal_succotash.model;

import java.util.Map;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class User {
    String name;
    String email;
    Long timeStampJoined;
    Map<String,Object> chats;

    public User() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getChats() {
        return chats;
    }

    public void setChats(Map<String, Object> chats) {
        this.chats = chats;
    }

    public User(String name, String email, Long timeStampJoined) {
        this.name = name;
        this.email = email;
        this.timeStampJoined = timeStampJoined;
    }

    public String getEmail() {
        return email;
    }

    public Long getTimeStampJoined() {
        return timeStampJoined;
    }

    public String getName() {
        return name;
    }
}
