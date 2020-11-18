package dk.events.a6.usecases.entities;

public class License {
    private User user;
    private Event event;
    protected License(){

    }
    @Override
    public String toString() {
        return "License{" +
                "user=" + user +
                ", event=" + event +
                '}';
    }

    public static LicenseBuilder newBuilder(){
        return new LicenseBuilder();
    }

    public static class LicenseBuilder {
        private User user = User.newUserBuilder().build();
        private Event event = Event.newBuilder().build();

        private LicenseBuilder(){}

        public License build(){
            License license = new License();
            license.setUser(this.user);
            license.setEvent(this.event);
            return license;
        }
        public LicenseBuilder withUser(User user){
            this.user = user;
            return this;
        }
        public LicenseBuilder withEvent(Event event){
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
