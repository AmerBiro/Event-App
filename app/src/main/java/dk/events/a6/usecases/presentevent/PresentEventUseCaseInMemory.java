package dk.events.a6.usecases.presentevent;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.usecases.entities.User;

public class PresentEventUseCaseInMemory implements PresentEventUseCase{
    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        return new ArrayList<>();
    }
}
