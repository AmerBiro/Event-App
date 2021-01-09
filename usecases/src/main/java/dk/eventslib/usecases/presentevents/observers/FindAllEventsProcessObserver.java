package dk.eventslib.usecases.presentevents.observers;

import java.util.List;

import dk.eventslib.entities.Event;

public interface FindAllEventsProcessObserver {
    void starting();
    void pending();
    void onSuccess(List<Event> events);
    void onFailure(Exception e);
}
