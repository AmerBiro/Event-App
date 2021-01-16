package dk.eventslib.entities;

import dk.eventslib.entities.Entity;

public class License extends Entity {
    public enum LicenseType {VIEWING, PARTICIPATING}

    private User user;
    private Event event;
    private LicenseType licenseType;

    protected License(){

    }

    @Override
    public String toString() {
        return "License{" +
                "dk.events.a6.user=" + user +
                ", event=" + event +
                ", licenseType=" + licenseType +
                '}';
    }

    public static LicenseBuilder newBuilder(){
        return new LicenseBuilder();
    }

    public static class LicenseBuilder {
        private String id;
        private User user = User.newUserBuilder().build();
        private Event event = Event.newBuilder().build();
        private LicenseType licenseType;

        private LicenseBuilder(){}

        public License build(){
            License license = new License();
            license.setId(this.id);
            license.setUser(this.user);
            license.setEvent(this.event);
            license.setLicenseType(this.licenseType);
            return license;
        }
        public LicenseBuilder withId(String id){
            this.id = id;
            return this;
        }
        public LicenseBuilder withUser(User user){
            this.user = user;
            return this;
        }
        public LicenseBuilder withEvent(Event event){
            this.event = event;
            return this;
        }
        public LicenseBuilder withLicenseType(LicenseType licenseType){
            this.licenseType = licenseType;
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

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
}
