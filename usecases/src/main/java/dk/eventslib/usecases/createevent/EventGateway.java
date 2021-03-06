package dk.eventslib.usecases.createevent;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObserver;

public interface EventGateway {
     void findAllEventsAsync();
     Event findEventByTitle(String title);
     void delete(Event event);
     void createEventAsync(Event event);

     Event getEvent(String id);

    default void addFindAllEventsProcessObserver(FindAllEventsProcessObserver findAllEventsProcessObserver){
        throw new RuntimeException("addFindAllEventsProcessObserver is not implemented");
    }

    class EventCreationException extends RuntimeException{
          public EventCreationException(String msg){
               super(msg);
          }
     }


}
