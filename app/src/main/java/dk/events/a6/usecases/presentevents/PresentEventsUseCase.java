package dk.events.a6.usecases.presentevents;

import java.util.List;

import dk.events.entities.Event;
import dk.events.entities.License;
import dk.events.entities.User;


public interface PresentEventsUseCase {

    List<PresentableEvent> presentEvents(User loggedUser);
    boolean hasLicenseFor(License.LicenseType licenseType, User user, Event event);
}
