package dk.events.a6.android;

import android.app.Application;

import dk.events.a6.android.usecases.presentevents.PresentEventsControllerImpl;
import dk.events.a6.android.usecases.presentevents.PresentEventsPresenterAsyncImpl;
import dk.eventslib.gatewayimpl.EventGatewayFirebaseImpl;
import dk.eventslib.usecases.presentevents.PresentEventsUseCaseImpl;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        _Context.eventGateway = new EventGatewayFirebaseImpl();
        _Context.bruceAlmighty = new BruceAlmighty();


        //present events use case
        _Context.presentEventsPresenterAsync = new PresentEventsPresenterAsyncImpl();

        PresentEventsUseCaseImpl presentEventsUseCase = new PresentEventsUseCaseImpl();
        presentEventsUseCase.setEventGateway(_Context.eventGateway);
        presentEventsUseCase.setPresentEventsPresenterAsync(_Context.presentEventsPresenterAsync);

        _Context.presentEventsController = new PresentEventsControllerImpl(presentEventsUseCase);
        _Context.presentEventsUseCase = presentEventsUseCase;

    }
}
