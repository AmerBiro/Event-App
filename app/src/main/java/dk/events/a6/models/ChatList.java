package dk.events.a6.models;

public class ChatList {
    private String name;
    private String description;
    private String date;
    private int image;



    public ChatList(String name, String description, String date, int image) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.image = image;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
