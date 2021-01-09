package dk.eventslib.usecases;

public interface CreateEventProcessObservable {
    void addCreateEventProcessObserver(CreateEventProcessObserver observer);
    void removeCreateEventProcessObserver(CreateEventProcessObserver observer);
}
