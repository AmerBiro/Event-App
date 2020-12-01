package dk.eventslib.usecases.createevent;

import java.util.List;

import dk.eventslib.entities.Event;

public interface EventGateway {
     List<Event> findAllEvents();
     Event findEventByTitle(String title);
     void delete(Event event);
     Event createEvent(Event event);

     Event getEvent(String id);

     class EventCreationException extends RuntimeException{
          public EventCreationException(String msg){
               super(msg);
          }
     }


}
