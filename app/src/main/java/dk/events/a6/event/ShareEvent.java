package dk.events.a6.event;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import dk.events.a6.mvvm.model.EventModel;

public class ShareEvent {

    private String image_url, name, address, date, time, age_range, type, description, distance;
    private String creator_name, creator_gender, creator_age;
    private int cost;
    private List<EventModel> eventModels;
    private int position;
    private Activity activity;

    public ShareEvent(List<EventModel> eventModels, Activity activity) {
        this.eventModels = eventModels;
        this.activity = activity;
    }

    public void shareEvent(int position) {
        this.position = position;

        this.image_url = this.eventModels.get(this.position).getImage();
        this.name = this.eventModels.get(this.position).getName();
        this.address = this.eventModels.get(this.position).getAddress();
        this.date = this.eventModels.get(this.position).getDate();
        this.time = this.eventModels.get(this.position).getTime();
        this.age_range = this.eventModels.get(this.position).getAge_range();
        this.type = this.eventModels.get(this.position).getType();
        this.description = this.eventModels.get(this.position).getDescription();
        this.distance = this.eventModels.get(this.position).getDistance();
        this.creator_name = this.eventModels.get(this.position).getCreator_name();
        this.creator_gender = this.eventModels.get(this.position).getCreator_gender();
        this.creator_age = this.eventModels.get(this.position).getCreator_age();
        this.cost = this.eventModels.get(this.position).getCost();

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, this.image_url + "\n\nBy:\n" +
                "Creator Name: " + this.creator_name + "\n" +
                "Creator Sex: " + this.creator_gender + "\n" +
                "Creator Age: " + this.creator_age + "\n" + "\n" +
                "Details\n" +
                "Event's Name: " + this.name + "\n" +
                "Event's Cost: " + this.cost + " DKK" +  "\n" +
                "Event's Location: " + this.address + "\n" +
                "Event's Date: " + this.date + "\n" +
                "Event's Time: " + this.time + "\n" +
                "Event's Age Range: " + this.age_range + "\n" +
                "Event's Type: " + this.type + "\n" +
                "Event's Description: " + this.description);
        share.setType("text/plain");
        activity.startActivity(share);

    }
}
