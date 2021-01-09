package dk.eventslib.usecases.presentevents;

import java.util.List;

import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.entities.Event;
import dk.eventslib.entities.User;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObserver;

public class PresentEventsUseCaseImpl implements PresentEventsUseCase, FindAllEventsProcessObserver {
    private EventGateway eventGateway;
    private PresentEventsPresenterAsync presentEventsPresenterAsync;

    @Override
    public void presentEventsAsync(User loggedUser) {
        eventGateway.addFindAllEventsProcessObserver(this);
        eventGateway.findAllEventsAsync();
    }


    @Override
    public void starting() {
        presentEventsPresenterAsync.startPresenting();
    }

    @Override
    public void pending() {
        presentEventsPresenterAsync.pending();
    }

    @Override
    public void onSuccess(List<Event> events) {
        presentEventsPresenterAsync.presentEvents(events);
    }

    @Override
    public void onFailure(Exception e) {
        presentEventsPresenterAsync.presentError(e);
    }


    public PresentEventsPresenterAsync getPresentEventsPresenterAsync() {
        return presentEventsPresenterAsync;
    }

    public void setPresentEventsPresenterAsync(PresentEventsPresenterAsync presentEventsPresenterAsync) {
        this.presentEventsPresenterAsync = presentEventsPresenterAsync;
    }

    public EventGateway getEventGateway() {
        return eventGateway;
    }

    public void setEventGateway(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }
}
