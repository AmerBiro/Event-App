package dk.kaloyan.a6;

import java.util.UUID;

import dk.kaloyan.a6.usecases.CreateEventInputPort;
import dk.kaloyan.a6.usecases.CreateEventUseCase;
import dk.kaloyan.a6.usecases.entities.Event;

public class CreateEventInputPortImpl implements CreateEventInputPort {
    private final CreateEventUseCase useCase;

    public CreateEventInputPortImpl(CreateEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void createEvent(String title) {
        Event event = new Event();
        event.setTitle(title);
        event.setId(UUID.randomUUID().toString());
        useCase.createEvent(event);
    }
}
