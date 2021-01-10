package dk.events.a6.android.usecases.presentevents;

import java.util.ArrayList;
import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.presentevents.PresentEventsPresenterAsync;

public class PresentEventsPresenterAsyncImpl implements PresentEventsPresenterAsync {

    private PresentEventsPresenterObserver presentEventsPresenterObserver;

    @Override
    public void presentEvents(List<Event> events) {
        List<PresentableEvent> presentableEvents = new ArrayList<>();
        for (Event event : events){
            presentableEvents.add(
                    new PresentableEvent(
                            "presented title: " + event.getTitle(),
                            "presented description: " + event.getDescription(),
                            event.getImageLocation()));
        }
        presentEventsPresenterObserver.onSuccess(presentableEvents);
    }

    @Override
    public void presentError(Exception exception) {
        presentEventsPresenterObserver.onFailure("presented error: " + exception.getMessage());
    }

    @Override
    public void startPresenting() {
        presentEventsPresenterObserver.starting();
    }

    @Override
    public void pending() {
        presentEventsPresenterObserver.pending();
    }

    public void setPresentEventsPresenterObserver(PresentEventsPresenterObserver presentEventsPresenterObserver) {
        this.presentEventsPresenterObserver = presentEventsPresenterObserver;
    }
}
