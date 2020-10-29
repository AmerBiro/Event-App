package dk.events.a6;

import java.util.UUID;

import dk.events.a6.usecases.CreateEventInputPort;
import dk.events.a6.usecases.CreateEventUseCase;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.User;

public class CreateEventInputPortImpl implements CreateEventInputPort {
    private final CreateEventUseCase useCase;

    public CreateEventInputPortImpl(CreateEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void createEvent(CreateEventViewModel vm) {
        User owner = User.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withFirstName(vm.ownerFirstName)
                .withLastName(vm.ownerLastName)
                .build();

        Event event = Event.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withTitle(vm.title)
                .withDescription(vm.description)
                .withOwner(owner)
                .build();

        event.setImages(vm.createEventImages);

        useCase.createEvent(event);
    }
}
