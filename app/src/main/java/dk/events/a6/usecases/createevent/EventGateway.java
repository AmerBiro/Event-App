package dk.events.a6.usecases.createevent;

import java.util.List;

import dk.eventslib.entities.Event;

public interface EventGateway {
     //new
     //License createLicense(License license);
     //List<License> findLicensesForUserAndEvent(User user, Event event);

     //User createUser(User user);
     //User findUser(String userName);

     List<Event> findAllEvents();
     void delete(Event event);
     Event findEventByTitle(String title);
     //end new

     Event createEvent(Event event);

     Event getEvent(String id);


}
