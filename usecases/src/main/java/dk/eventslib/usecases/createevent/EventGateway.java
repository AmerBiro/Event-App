package dk.eventslib.usecases.createevent;

import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.presentevents.PresentEventsUseCaseImpl;
import dk.eventslib.usecases.presentevents.observers.FindAllEventsProcessObserver;

public interface EventGateway {
     void findAllEventsAsync();
     Event findEventByTitle(String title);
     void delete(Event event);
     Event createEvent(Event event);

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
