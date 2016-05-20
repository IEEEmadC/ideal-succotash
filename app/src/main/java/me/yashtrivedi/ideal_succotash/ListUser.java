package me.yashtrivedi.ideal_succotash;

import java.io.File;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class ListUser {
    String name, from, to, roll;
    File img;

    public ListUser(String name, String from, String to, File img) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.img = img;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll.split("@")[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }
}
