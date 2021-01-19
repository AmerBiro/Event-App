package dk.events.a6.event;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import dk.events.a6.mvvm.model.EventModel;

public class GetEventData {

    private String event_id;
    private String image, name, address, date, time, type, description;
    private String creator_id, creator_image, creator_name, creator_gender, creator_age;
    private String distance;
    private int cost, min, max;
    private List<EventModel> eventModels;
    private int position;


    public GetEventData(List<EventModel> eventModels, int position) {
        this.eventModels = eventModels;
        this.position = position;
        this.event_id = this.eventModels.get(this.position).getEvent_id();
        this.image = this.eventModels.get(this.position).getImage();
        this.name = this.eventModels.get(this.position).getName();
        this.address = this.eventModels.get(this.position).getAddress();
        this.date = this.eventModels.get(this.position).getDate();
        this.time = this.eventModels.get(this.position).getTime();
        this.min = this.eventModels.get(this.position).getMin();
        this.max = this.eventModels.get(this.position).getMax();
        this.type = this.eventModels.get(this.position).getType();
        this.description = this.eventModels.get(this.position).getDescription();
        this.distance = this.eventModels.get(this.position).getDistance();
        this.creator_id = this.eventModels.get(this.position).getCreator_id();
        this.creator_image = this.eventModels.get(this.position).getCreator_image();
        this.creator_name = this.eventModels.get(this.position).getCreator_name();
        this.creator_gender = this.eventModels.get(this.position).getCreator_gender();
        this.creator_age = this.eventModels.get(this.position).getCreator_age();
        this.cost = this.eventModels.get(this.position).getCost();
    }


    public String getEvent_id() {
        return event_id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public String getCreator_image() {
        return creator_image;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public String getCreator_gender() {
        return creator_gender;
    }

    public String getCreator_age() {
        return creator_age;
    }

    public String getDistance() {
        return distance;
    }

    public int getCost() {
        return cost;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
