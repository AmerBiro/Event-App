package dk.events.a6.usecases.entities;

public class ParticipationLicense extends License {
    private ParticipationLicense(){ }

    public static ParticipationLicenseBuilder newParticipationLicenseBuilder() {
        return new ParticipationLicenseBuilder();
    }

    public static class ParticipationLicenseBuilder {
        private User user;
        private Event event;

        public ParticipationLicenseBuilder withUser(User user){
            this.user = user;
            return this;
        }
        public ParticipationLicenseBuilder withEvent(Event event){
            this.event = event;
            return this;
        }
        public ParticipationLicense build(){
            ParticipationLicense pl = new ParticipationLicense();
            pl.setEvent(this.event);
            pl.setUser(this.user);
            return pl;
        }

    }

}
