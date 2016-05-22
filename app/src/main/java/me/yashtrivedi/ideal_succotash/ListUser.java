package me.yashtrivedi.ideal_succotash;

import java.io.File;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class ListUser {
    String userName, area, roll, carNo;
    int carCapacity;
    Boolean toNirma;
    //File img;

    public ListUser(){

    }

    public boolean equals(ListUser user){
        return (this.roll.equals(user.getRoll()));
    }

    public int getCapacity() {
        return carCapacity;
    }

    public Boolean getToNirma() {
        return toNirma;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
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
