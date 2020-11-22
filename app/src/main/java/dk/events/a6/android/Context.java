package dk.events.a6.android;

import dk.eventslib.usecases.LicenseGateway;
import dk.eventslib.usecases.UserGateway;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.usecases.presentevents.PresentEventsUseCase;


public class Context {
    public static EventGateway eventGateway ;
    public static UserGateway userGateway;
    public static LicenseGateway licenseGateway;

    public static PresentEventsUseCase presentEventsUseCase;

    public static BruceAlmighty bruceAlmighty;
}
