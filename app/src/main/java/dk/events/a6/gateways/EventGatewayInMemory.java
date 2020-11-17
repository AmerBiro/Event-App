package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.events.a6.usecases.EventGateway;
import dk.events.a6.usecases.entities.Entity;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public class EventGatewayInMemory implements EventGateway {
    private static Map<String,Event> eventsMap = new HashMap<>();
    private static Map<String,User> usersMap = new HashMap<>();
    private List<License> licenses = new ArrayList<>();

    //new
    @Override
    public List<License> findLicensesForUserAndEvent(User user, Event event) {
        List<License> returnedLicenses = new ArrayList<>();
        for (License l : licenses){
            if(l.getUser().isSame(user) && l.getEvent().isSame(event)){
                returnedLicenses.add(l);
            }
        }
        return returnedLicenses;
    }

    @Override
    public License createLicense(License license) {
        licenses.add(license);
        return license;
    }


    @Override
    public User createUser(User user) {
        usersMap.put(user.getId(), (User) setWithId(user));
        return user;
    }

    private Entity setWithId(Entity entity) {
        if(entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());
        return entity;
    }

    @Override
    public User findUser(String userName) {
        for (User user : (List<User>) usersMap.values()) {
            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        return null;
    }


    @Override
    public Event findEventByTitle(String title) {
        for (Event e : eventsMap.values()) {
            if(e.getTitle().equals(title)){
                return e;
            }
        }
        return null;
    }

    public static void setEvents(List<Event> events) {
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

