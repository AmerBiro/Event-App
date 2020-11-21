package dk.events.a6.android;

import dk.events.a6.usecases.createevent.EventGateway;
import dk.events.a6.usecases.LicenseGateway;
import dk.events.a6.usecases.UserGateway;
import dk.events.a6.usecases.presentevents.PresentEventsUseCase;


public class Context {
    public static EventGateway eventGateway ;
    public static UserGateway userGateway;
    public static LicenseGateway licenseGateway;

    public static PresentEventsUseCase presentEventsUseCase;

    public static BruceAlmighty bruceAlmighty;
}