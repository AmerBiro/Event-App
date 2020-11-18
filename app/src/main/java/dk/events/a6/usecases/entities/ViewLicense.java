package dk.events.a6.usecases.entities;

public class ViewLicense extends License {
    private ViewLicense(){ }

    public static ViewLicenseBuilder newViewLicenseBuilder() {
        return new ViewLicenseBuilder();
    }

    public static class ViewLicenseBuilder {
        private User user;
        private Event event;

        public ViewLicenseBuilder withUser(User user){
            this.user = user;
            return this;
        }
        public ViewLicenseBuilder withEvent(Event event){
            this.event = event;
            return this;
        }
        public ViewLicense build(){
            ViewLicense vl = new ViewLicense();
            vl.setEvent(this.event);
            vl.setUser(this.user);
            return vl;
        }

    }
}
