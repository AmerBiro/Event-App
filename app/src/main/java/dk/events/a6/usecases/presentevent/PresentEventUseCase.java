package dk.events.a6.usecases.presentevent;

import java.util.List;

import dk.events.a6.usecases.entities.User;

public interface PresentEventUseCase {

    List<PresentableEvent> presentEvents(User loggedUser);
}
