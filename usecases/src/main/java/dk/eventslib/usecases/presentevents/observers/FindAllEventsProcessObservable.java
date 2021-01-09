package dk.eventslib.usecases.presentevents.observers;

public interface FindAllEventsProcessObservable {
    void addFindAllEventsProcessObserver(FindAllEventsProcessObserver observer);
    void removeFindAllEventsProcessObserver(FindAllEventsProcessObserver observer);
}
