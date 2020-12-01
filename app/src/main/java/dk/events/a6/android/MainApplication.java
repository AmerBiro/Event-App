package dk.events.a6.android;

import android.app.Application;

import dk.eventslib.gateways.ObservableEventGatewayInMemory;
import dk.eventslib.usecases.presentevents.PresentEventsUseCaseImpl;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        Context.observableEventGateway = new ObservableEventGatewayInMemory();
        Context.bruceAlmighty = new BruceAlmighty();
        Context.presentEventsUseCase = new PresentEventsUseCaseImpl(Context.observableEventGateway, Context.licenseGateway);
    }
}
