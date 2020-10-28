package dk.events.a6.models;

public class FavoriteEventList {

    private String name;
    private String description;
    private int event_image;
    private int profile_image;

    public FavoriteEventList(String name, String description, int event_image, int profile_image) {
        this.name = name;
        this.description = description;
        this.event_image = event_image;
        this.profile_image = profile_image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEvent_image() {
        return event_image;
    }

    public void setEvent_image(int event_image) {
        this.event_image = event_image;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }
}
