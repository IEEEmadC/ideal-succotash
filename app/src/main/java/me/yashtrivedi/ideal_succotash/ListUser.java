package me.yashtrivedi.ideal_succotash;

import java.io.File;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class ListUser {
    String userName, area, roll, carNo, carCapacity;
    Boolean toNirma;
    //File img;

    public ListUser(){

    }

    public String getCapacity() {
        return carCapacity;
    }

    public Boolean getToNirma() {
        return toNirma;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll.split("@")[0];
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getArea() {
        return area;
    }

    public String getCarNo() {
        return carNo;
    }
}
