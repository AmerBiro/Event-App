package dk.events.a6.usecases.entities;

public class License {
    private User user;
    private Event event;
    private License(){

    }
    @Override
    public String toString() {
        return "License{" +
                "user=" + user +
                ", event=" + event +
                '}';
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private User user = User.newUserBuilder().build();
        private Event event = Event.newBuilder().build();

        private Builder(){}

        public License build(){
            License license = new License();
            license.setUser(this.user);
            license.setEvent(this.event);
            return license;
        }
        public Builder withUser(User user){
            this.user = user;
            return this;
        }
        public Builder withEvent(Event event){
            this.event = event;
            return this;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
