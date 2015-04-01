package com.trojanow.model;

import com.trojanow.sensor.EnvironmentInfo;

import java.util.List;

/**
 * Created by pabloivan57 on 3/27/15.
 */
public class Post {
    private String title;
    private String description;
    private Boolean anonymous;
    private Location location;
    private List<MediaType> media;
    private User publisher;
    private EnvironmentInfo environmentInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<MediaType> getMedia() {
        return media;
    }

    public void setMedia(List<MediaType> media) {
        this.media = media;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }


    public EnvironmentInfo getEnvironmentInfo() {
        return environmentInfo;
    }

    public void setEnvironmentInfo(EnvironmentInfo environmentInfo) {
        this.environmentInfo = environmentInfo;
    }
}