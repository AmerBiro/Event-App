package dk.kaloyan.a6.usecases;

import dk.kaloyan.a6.usecases.entities.Event;

public class CreateEventUseCase{
    private EventGateway eventGateway;
    private CreateEventOutputPort outputPort;

    public CreateEventUseCase() { }

    public Event getEvent(String id) {
        return eventGateway.getEvent(id);
    }

    public void createEvent(Event event) {
        eventGateway.createEvent(event);
        outputPort.show(String.format("Event with Id: %s and Title: %s was created!", event.getId(), event.getTitle()));
    }

    public EventGateway getEventGateway() {
        return eventGateway;
    }

    public void setEventGateway(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    public CreateEventOutputPort getOutputPort() {
        return outputPort;
    }

    public void setOutputPort(CreateEventOutputPort outputPort) {
        this.outputPort = outputPort;
    }
}
