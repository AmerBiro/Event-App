package dk.events.a6.android;

import java.util.UUID;

import dk.events.a6.usecases.createevent.CreateEventInputPort;
import dk.events.a6.usecases.createevent.CreateEventUseCase;
import dk.events.entities.Event;
import dk.events.entities.User;

public class CreateEventInputPortImpl implements CreateEventInputPort {
    private final CreateEventUseCase useCase;

    public CreateEventInputPortImpl(CreateEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void createEvent(CreateEventViewModel vm) {
        User owner = User.newUserBuilder()
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
