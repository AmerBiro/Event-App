package dk.events.a6.usecases;

import dk.events.a6.CreateEventViewModel;

public interface CreateEventInputPort {
    void createEvent(CreateEventViewModel vm);
}
