package com.trojanow.model;

/**
 * Created by pabloivan57 on 3/28/15.
 */
public class Message {
    private String message;
    private User sender;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
