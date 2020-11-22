package dk.events.a6.android;

import java.util.UUID;

import dk.events.a6.android.BruceAlmighty;
import dk.eventslib.entities.User;
import dk.eventslib.usecases.LicenseGateway;
import dk.eventslib.usecases.UserGateway;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.usecases.presentevents.PresentEventsUseCase;


public class Context {
    public static EventGateway eventGateway ;
    public static UserGateway userGateway;
    public static LicenseGateway licenseGateway;
    public static User demoUser = User.newUserBuilder().withId(UUID.randomUUID().toString()).withFirstName("Bob").withLastName("Marley").build();
    public static BruceAlmighty bruceAlmighty = new BruceAlmighty().setLoggedInUser(demoUser);
    public static PresentEventsUseCase presentEventsUseCase;

}
