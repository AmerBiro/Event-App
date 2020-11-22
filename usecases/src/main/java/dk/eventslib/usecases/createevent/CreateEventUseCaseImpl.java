package dk.eventslib.usecases.createevent;


import dk.eventslib.entities.Event;

public class CreateEventUseCaseImpl implements CreateEventInputPort {
    private EventGateway eventGateway;
    private CreateEventOutputPort outputPort;

    public CreateEventUseCaseImpl() { }

    public Event getEvent(String id) {
        return eventGateway.getEvent(id);
    }

    public void createEvent(Event event) {
        eventGateway.createEvent(event);
        outputPort.show(
                String.format(
                        "Event with Id: %s, Title: %s, Owner: %s was created!",
                        event.getId(), event.getTitle(), event.getOwner().toString()));
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
