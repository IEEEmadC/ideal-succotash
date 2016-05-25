package me.yashtrivedi.ideal_succotash.model;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class Threads {
    String name, email, key, msg;
    Long time;
    Boolean read;
    int unreadCount;

    public Threads(String name, String email, Long time, Boolean read, int unreadCount) {
        this.name = name;
        this.email = email;
        this.time = time;
        this.read = read;
        this.unreadCount = unreadCount;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Threads() {
    }

    public String getMessage(){
        return msg;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getTime() {
        return time;
    }

    public Boolean getRead() {
        return read;
    }

    public int getUnreadCount() {
        return unreadCount;
    }
}
