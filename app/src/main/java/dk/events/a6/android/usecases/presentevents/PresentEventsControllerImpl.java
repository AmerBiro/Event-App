package dk.events.a6.android.usecases.presentevents;

import dk.eventslib.usecases.presentevents.PresentEventsUseCase;

public class PresentEventsControllerImpl {

    private PresentEventsUseCase presentEventsUseCase;

    public PresentEventsControllerImpl(PresentEventsUseCase presentEventsUseCase) {

        this.presentEventsUseCase = presentEventsUseCase;
    }



    public PresentEventsUseCase getPresentEventsUseCase() {
        return presentEventsUseCase;
    }

    public void setPresentEventsUseCase(PresentEventsUseCase presentEventsUseCase) {
        this.presentEventsUseCase = presentEventsUseCase;
    }

    public void execute() {
        presentEventsUseCase.presentEventsAsync(null);
    }
}
