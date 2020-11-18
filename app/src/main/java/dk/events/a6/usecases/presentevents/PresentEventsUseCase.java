package dk.events.a6.usecases.presentevents;

import java.util.List;

import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.User;

public interface PresentEventsUseCase {

    List<PresentableEvent> presentEvents(User loggedUser);
    boolean hasParticipationLicense(User user, Event event);
    boolean hasViewLicense(User user, Event event);
}
