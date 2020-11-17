package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.events.a6.usecases.EventGateway;
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
        return licenses;
    }

    @Override
    public void createLicense(License license) {
        licenses.add(license);
    }


    @Override
    public void createUser(User user) {
        //User user = User.newBuilder().withId(UUID.randomUUID().toString()).withUserName(userName).build();
        usersMap.put(user.getId(),user);
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
        return (List<Event>) eventsMap.values();
    }

    @Override
    public void delete(Event event) {
        eventsMap.remove(event.getId());
    }
    //end new


    @Override
    public void createEvent(Event event) {
        eventsMap.put(event.getId(), event);
    }

    @Override
    public Event getEvent(String id) {
        return eventsMap.get(id);
    }

    public List<Event> getEvents() {
        return new ArrayList<>(eventsMap.values()) ;
    }
}

