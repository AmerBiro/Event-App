package dk.eventslib.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.eventslib.entities.Entity;

public class Event extends Entity {
    private String title;
    private String description;
    private User owner = User.newUserBuilder().build();
    private List<User> participants = new ArrayList<>();
    private List<ImageDetails> images = new ArrayList<>();
    private Date startDate;

    private Event(){
    }

    public String toString(){
        return String.format(
                "Event: id = %s, title = %s, description = %s, startDate = %s, owner = %s, participants = %s",
                id,
                title,
                description,
                startDate.toString(),
                owner.toString(),
                participants.stream().map(u -> u.toString()).reduce("", String::concat));
    }

    public static EventBuilder newBuilder(){
        return new EventBuilder();
    }

    public void setImages(List<ImageDetails> images) {
        this.images = images;
    }

    public List<ImageDetails> getImages() {
        return images;
    }



    public static class EventBuilder{
        private String title;
        private String id;
        private String description;
        private User owner = User.newUserBuilder().build();
        private List<User> participants = new ArrayList<>();
        private List<ImageDetails> images = new ArrayList<>();
        private Date startDate;

        private EventBuilder(){
        }
        public Event build(){
            Event event = new Event();
            event.setId(id);
            event.setTitle(title);
            event.setDescription(description);
            event.setOwner(owner);
            event.setParticipants(participants);
            event.setImages(images);
            event.setStartDate(startDate);
            return event;
        }

        public void withImages(List<ImageDetails> images) {
            this.images = images;
        }

        public EventBuilder withId(String id) {
            this.id = id;
            return this;
        }
        public EventBuilder withTitle(String title) {
            this.title = title;
            return this;
        }
        public EventBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
        public EventBuilder withOwner(User owner) {
            this.owner = owner;
            return this;
        }
        public EventBuilder withParticipants(List<User> participants) {
            this.participants = participants;
            return this;
        }
        public EventBuilder withStartDate(Date startDate){
            this.startDate = startDate;
            return this;
        }
    }



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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }
}
