package dk.events.a6.usecases.presentevents;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.Context;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public class PresentEventsUseCaseInMemory implements PresentEventsUseCase {
    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        return new ArrayList<>();
    }

    @Override
    public boolean isLicensedToParticipate(User user, Event event) {
        List<License> licenses = Context.eventGateway.findLicensesForUserAndEvent(user, event);
        return !licenses.isEmpty();
    }
}
