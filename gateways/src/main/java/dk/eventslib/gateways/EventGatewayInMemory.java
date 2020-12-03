package dk.eventslib.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.eventslib.usecases.ProcessObserver;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.entities.Event;

public class EventGatewayInMemory extends BaseGatewayInMemory<Event> implements EventGateway {
    @Override
    public Event findEventByTitle(String title) {
        for (Event e : eventsMap.values()) {
            if(e.getTitle().equals(title)){
                return e;
            }
        }
        return null;
    }

    public  void setEvents(List<Event> events) { //static?
        eventsMap = new HashMap<>();
        for (Event e: events)
            eventsMap.put(e.getId(),e);
    }

    @Override
    public List<Event> findAllEvents() {
        return new ArrayList<>(eventsMap.values());
    }

    @Override
    public void delete(Event event) {
        eventsMap.remove(event.getId());
    }

    @Override
    public Event createEvent(Event event) {
        eventsMap.put(event.getId(), (Event) setWithId(event));
        return event;
    }

    @Override
    public Event getEvent(String id) {
        return eventsMap.get(id);
    }

    public List<Event> getEvents() {
        return new ArrayList<>(eventsMap.values()) ;
    }
}

