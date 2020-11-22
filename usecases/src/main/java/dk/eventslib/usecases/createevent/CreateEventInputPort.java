package dk.eventslib.usecases.createevent;

import dk.eventslib.entities.Event;

public interface CreateEventInputPort {
    void createEvent(Event event);
}
