package dk.eventslib.usecases;

import dk.eventslib.entities.Event;

public interface ProcessObserver {
    void starting();
    void pending();
    void processed(Event event);
}