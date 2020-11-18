package dk.events.a6.usecases.createevent;

import dk.events.a6.android.CreateEventViewModel;

public interface CreateEventInputPort {
    void createEvent(CreateEventViewModel vm);
}
