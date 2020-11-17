package dk.events.a6.app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.Context;
import dk.events.a6.gateways.EventGatewayInMemory;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        Context.eventGateway = new EventGatewayInMemory();
    }
}
