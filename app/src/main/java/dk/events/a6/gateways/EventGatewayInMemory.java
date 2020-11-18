package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.events.a6.usecases.EventGateway;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public class EventGatewayInMemory extends BaseGatewayInMemory implements EventGateway {
    private  Map<String,Event> eventsMap = new HashMap<>(); //static?

    //new
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
    //end new


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

