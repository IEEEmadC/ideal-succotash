package me.yashtrivedi.ideal_succotash.model;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class Message {
    String from, msg;
    Long time;

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
