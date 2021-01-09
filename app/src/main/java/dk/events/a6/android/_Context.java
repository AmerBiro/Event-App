package dk.events.a6.android;

import java.util.UUID;

import dk.events.a6.android.usecases.presentevents.PresentEventsControllerImpl;
import dk.events.a6.android.usecases.presentevents.PresentEventsPresenterAsyncImpl;
import dk.eventslib.entities.User;
import dk.eventslib.usecases.LicenseGateway;
import dk.eventslib.usecases.UserGateway;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.usecases.presentevents.PresentEventsPresenterAsync;
import dk.eventslib.usecases.presentevents.PresentEventsUseCase;


public class _Context {
    public static EventGateway eventGateway;
    public static UserGateway userGateway;
    public static LicenseGateway licenseGateway;
    public static User demoUser = User.newUserBuilder().withId(UUID.randomUUID().toString()).withFirstName("Bob").withLastName("Marley").build();
    public static BruceAlmighty bruceAlmighty = new BruceAlmighty().setLoggedInUser(demoUser);
    public static PresentEventsUseCase presentEventsUseCase;

    public static PresentEventsControllerImpl presentEventsController;
    public static PresentEventsPresenterAsync presentEventsPresenterAsync;
}
