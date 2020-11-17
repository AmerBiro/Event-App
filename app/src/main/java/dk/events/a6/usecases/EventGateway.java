package dk.events.a6.usecases;

import java.util.List;

import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public interface EventGateway {
     //new
     License createLicense(License license);
     List<License> findLicensesForUserAndEvent(User user, Event event);

     User createUser(User user);
     User findUser(String userName);

     List<Event> findAllEvents();
     void delete(Event event);
     Event findEventByTitle(String title);
     //end new

     void createEvent(Event event);

     Event getEvent(String id);


}
