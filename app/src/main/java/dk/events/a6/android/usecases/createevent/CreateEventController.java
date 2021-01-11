package dk.events.a6.android.usecases.createevent;

import java.util.UUID;

import dk.eventslib.usecases.createevent.CreateEventInputPort;
import dk.eventslib.entities.Event;
import dk.eventslib.entities.User;

public class CreateEventController {
    private final CreateEventInputPort useCase;

    public CreateEventController(CreateEventInputPort useCase) {
        this.useCase = useCase;
    }

    public void createEvent(CreateEventViewModel vm, User loggedInUser) {

        Event event = Event.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withTitle(vm.title)
                .withDescription(vm.description)
                .withOwner(loggedInUser)
                .build();

        event.setImages(vm.createEventImages);

        useCase.createEventAsync(event);
    }
}
