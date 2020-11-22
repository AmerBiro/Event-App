package dk.eventslib.usecases.presentevents;

import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.entities.License;
import dk.eventslib.entities.User;


public interface PresentEventsUseCase {

    List<PresentableEvent> presentEvents(User loggedUser);
    boolean hasLicenseFor(License.LicenseType licenseType, User user, Event event);
}
