package dk.eventslib.usecases;

import dk.eventslib.entities.Event;

public interface CreateEventProcessObserver {
    void starting();
    void pending();
    void onSuccess(Event event);
    void onFailure(Event event);
}