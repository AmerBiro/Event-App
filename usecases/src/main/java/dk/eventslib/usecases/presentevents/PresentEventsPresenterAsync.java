package dk.eventslib.usecases.presentevents;

import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObserver;

public interface PresentEventsPresenterAsync {

    void presentEvents(List<Event> events);
    void presentError(Exception exception);

    void startPresenting();

    void pending();
}
