package dk.events.a6.usecases.presentevents;

import java.util.List;

import dk.events.a6.entities.Event;
import dk.events.a6.entities.License.LicenseType;
import dk.events.a6.entities.User;

public interface PresentEventsUseCase {

    List<PresentableEvent> presentEvents(User loggedUser);
    boolean hasLicenseFor(LicenseType licenseType, User user, Event event);
}
