package dk.kaloyan.a6.usecases;

import dk.kaloyan.a6.usecases.entities.Event;

public class CreateEventUseCase {
    private EventGateway eventGateway;

    public CreateEventUseCase() { }

    public Event getEvent(String id) {
        return eventGateway.getEvent(id);
    }

    public void createEvent(Event event) {
        eventGateway.createEvent(event);
    }

    public EventGateway getEventGateway() {
        return eventGateway;
    }

    public void setEventGateway(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }
}
