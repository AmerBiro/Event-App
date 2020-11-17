package dk.events.a6.app;

import android.app.Application;

import dk.events.a6.BruceAlmighty;
import dk.events.a6.Context;
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.presentevents.PresentEventsUseCaseInMemory;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        Context.eventGateway = new EventGatewayInMemory();
        Context.bruceAlmighty = new BruceAlmighty();
        Context.presentEventsUseCase = new PresentEventsUseCaseInMemory();
    }
}
