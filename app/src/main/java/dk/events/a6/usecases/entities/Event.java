package dk.events.a6.usecases.entities;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String id = "";
    private String title = "";
    private String description = "";
    private User owner = User.newBuilder().build();
    private List<User> participants = new ArrayList<>();

    private Event(){
    }

    public String toString(){
        return String.format(
                "Event: id = %s, title = %s, description = %s, owner = %s, participants = %s",
                id,
                title,
                description,
                owner.toString(),
                participants.stream().map(u -> u.toString()).reduce("", String::concat));
    }

    public static EventBuilder newBuilder(){
        return new EventBuilder();
    }

    public static class EventBuilder{
        private String title = "";
        private String id = "";
        private String description = "";
        private User owner = User.newBuilder().build();
        private List<User> participants = new ArrayList<>();
        private EventBuilder(){
        }
        public Event build(){
            Event event = new Event();
            event.setId(id);
            event.setTitle(title);
            event.setDescription(description);
            event.setOwner(owner);
            event.setParticipants(participants);
            return event;
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
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
