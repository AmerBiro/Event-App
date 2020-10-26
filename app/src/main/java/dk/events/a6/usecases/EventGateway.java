package dk.events.a6.usecases;

import dk.events.a6.usecases.entities.Event;

public interface EventGateway {
    public void createEvent(Event event);

    Event getEvent(String id);
}
