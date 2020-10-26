package dk.events.a6.gateways;

import java.util.HashMap;
import java.util.Map;

import dk.events.a6.usecases.EventGateway;
import dk.events.a6.usecases.entities.Event;

public class EventGatewayInMemory implements EventGateway {
    private static Map<String,Event> db = new HashMap<>();
    @Override
    public void createEvent(Event event) {
        db.put(event.getId(), event);
    }

    @Override
    public Event getEvent(String id) {
        return db.get(id);
    }
}
