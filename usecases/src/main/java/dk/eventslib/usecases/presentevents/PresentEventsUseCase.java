package dk.eventslib.usecases.presentevents;

import dk.eventslib.entities.User;


public interface PresentEventsUseCase {

    void presentEventsAsync(User loggedUser);
}
