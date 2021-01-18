package dk.events.a6.event;

import android.app.Activity;
import android.app.Dialog;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import dk.events.a6.R;

import static android.content.ContentValues.TAG;

public class EventTypeView implements View.OnClickListener {

    private String eventType;
    private ImageView sport, entertainment, food, music, culture, get_smarter, free;
    private Activity activity;
    private EditText editText;
    private Dialog dialog;

    public EventTypeView(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
    }

    public void showEventTypeDialog() {
        this.dialog = new Dialog(this.activity);
        dialog.setContentView(R.layout.event_event_type);
        dialog.getWindow().getAttributes().windowAnimations = R.style.event_type_anim;
        dialog.getWindow().getAttributes().windowAnimations = R.style.event_type_anim;
        dialog.show();

        this.sport = dialog.findViewById(R.id.sport);
        this.entertainment = dialog.findViewById(R.id.entertainment);
        this.food = dialog.findViewById(R.id.food_drink);
        this.music = dialog.findViewById(R.id.music);
        this.culture = dialog.findViewById(R.id.culture);
        this.get_smarter = dialog.findViewById(R.id.get_smarter);
        this.free = dialog.findViewById(R.id.free);

        sport.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        food.setOnClickListener(this);
        music.setOnClickListener(this);
        culture.setOnClickListener(this);
        get_smarter.setOnClickListener(this);
        free.setOnClickListener(this);
    }


    public String getEventType() {
        return eventType;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sport:
                eventType = "Sport";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.entertainment:
                eventType = "Entertainment";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.food_drink:
                eventType = "Food & Drink";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.music:
                eventType = "Music & Night Life";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.culture:
                eventType = "Culture";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.get_smarter:
                eventType = "Get Smarter";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
            case R.id.free:
                eventType = "Free";
                editText.setText(eventType);
                Toast.makeText(activity, eventType, 0).show();
                this.dialog.cancel();
                break;
                default:
        }



    }
}
