package dk.eventslib.usecases.createevent;


import dk.eventslib.entities.Event;

public class CreateEventUseCaseImpl implements CreateEventInputPort {
    private ObservableEventGateway observableEventGateway;
    private CreateEventOutputPort outputPort;

    public CreateEventUseCaseImpl() { }

    public Event getEvent(String id) {
        return observableEventGateway.getEvent(id);
    }

    public void createEvent(Event event) {
        observableEventGateway.createEvent(event);
        outputPort.show(
                String.format(
                        "Event with Id: %s, Title: %s, Owner: %s was created!",
                        event.getId(), event.getTitle(), event.getOwner()==null?"null":event.getOwner().toString()));
    }

    public ObservableEventGateway getEventGateway() {
        return observableEventGateway;
    }

    public void setEventGateway(ObservableEventGateway observableEventGateway) {
        this.observableEventGateway = observableEventGateway;
    }

    public CreateEventOutputPort getOutputPort() {
        return outputPort;
    }

    public void setOutputPort(CreateEventOutputPort outputPort) {
        this.outputPort = outputPort;
    }
}
