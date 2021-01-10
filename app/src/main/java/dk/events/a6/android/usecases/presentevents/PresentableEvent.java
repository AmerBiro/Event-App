package dk.events.a6.android.usecases.presentevents;

public class PresentableEvent {
    public String title = "default_title";
    public String description = "default_description";
    public String imageLocation;

    public PresentableEvent(String title, String description, String imageLocation) {
        this.title = title;
        this.description = description;
        this.imageLocation = imageLocation;
    }
}
