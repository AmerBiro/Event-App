package dk.eventslib.usecases;

import dk.eventslib.entities.Event;

public interface ProcessObserver {
    void starting();
    void pending();
    void onSuccess(Event event);
    void onFailure(Event event);

}