package dk.events.a6;

import java.util.UUID;

import dk.events.a6.usecases.CreateEventInputPort;
import dk.events.a6.usecases.CreateEventUseCase;
import dk.events.a6.usecases.entities.Event;

public class CreateEventInputPortImpl implements CreateEventInputPort {
    private final CreateEventUseCase useCase;

    public CreateEventInputPortImpl(CreateEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void createEvent(String title, String description) {
        Event event = new Event();
        event.setTitle(title);
        event.setId(UUID.randomUUID().toString());
        event.setDescription(description);
        useCase.createEvent(event);
    }
}
