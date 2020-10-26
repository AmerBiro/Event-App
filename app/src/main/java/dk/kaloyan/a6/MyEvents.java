package com.example.eventsapp;

public class MyEvents {

    private int event_background  ,event_avatar;
    private String event_title, event_distance;

    public MyEvents(int event_background, int event_avatar, String event_name, String event_distance) {
        this.event_background = event_background;
        this.event_avatar = event_avatar;
        this.event_title = event_name;
        this.event_distance = event_distance;
    }

    public int getEvent_background() {
        return event_background;
    }

    public void setEvent_background(int event_background) {
        this.event_background = event_background;
    }

    public int getEvent_avatar() {
        return event_avatar;
    }

    public void setEvent_avatar(int event_avatar) {
        this.event_avatar = event_avatar;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_distance() {
        return event_distance;
    }

    public void setEvent_distance(String event_distance) {
        this.event_distance = event_distance;
    }
}
