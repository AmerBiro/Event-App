package dk.events.a6.event;

import android.app.Activity;
import android.app.Dialog;

import android.widget.ImageView;

import dk.events.a6.R;

public class EventTypeView {

    private String eventType;
    private ImageView sport, entertainment, food, music, culture, get_smarter, free;


    public EventTypeView(String eventType, ImageView sport, ImageView entertainment, ImageView food, ImageView music, ImageView culture, ImageView get_smarter, ImageView free) {
        this.eventType = eventType;
        this.sport = sport;
        this.entertainment = entertainment;
        this.food = food;
        this.music = music;
        this.culture = culture;
        this.get_smarter = get_smarter;
        this.free = free;
    }

    public void showEventTypeDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.event_event_type);
        dialog.getWindow().getAttributes().windowAnimations = R.style.event_type_anim;
        dialog.getWindow().getAttributes().windowAnimations = R.style.event_type_anim;
        dialog.show();

    }
}
