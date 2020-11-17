package dk.events.a6.usecases;

import java.util.List;

import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.User;

public interface EventGateway {
     //new
     List<Event> findAllEvents();
     void delete(Event event);
     void createUser(User user);
     User findUser(String userName);
     //end new

     void createEvent(Event event);

     Event getEvent(String id);

}
