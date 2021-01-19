package com.example.mytest;

import com.google.firebase.firestore.DocumentId;

public class EventModel {

    @DocumentId
    private String event_id;
    private String image, name, address, date, time, type, description;
    private String creator_id, creator_image, creator_name, creator_gender, creator_age;
    private String distance;
    private int cost, min, max;


    public EventModel(String event_id, String image, String name, String address, String date, String time, String type, String description, String creator_id, String creator_image, String creator_name, String creator_gender, String creator_age, String distance, int cost, int min, int max) {
        this.event_id = event_id;
        this.image = image;
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
        this.creator_id = creator_id;
        this.creator_image = creator_image;
        this.creator_name = creator_name;
        this.creator_gender = creator_gender;
        this.creator_age = creator_age;
        this.distance = distance;
        this.cost = cost;
        this.min = min;
        this.max = max;
    }

    public EventModel() {

    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_image() {
        return creator_image;
    }

    public void setCreator_image(String creator_image) {
        this.creator_image = creator_image;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_gender() {
        return creator_gender;
    }

    public void setCreator_gender(String creator_gender) {
        this.creator_gender = creator_gender;
    }

    public String getCreator_age() {
        return creator_age;
    }

    public void setCreator_age(String creator_age) {
        this.creator_age = creator_age;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
