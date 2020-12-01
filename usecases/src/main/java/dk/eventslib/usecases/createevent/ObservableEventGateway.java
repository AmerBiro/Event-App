package dk.eventslib.usecases.createevent;

import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.usecases.ProcessObservable;
import dk.eventslib.usecases.ProcessObserver;

public interface ObservableEventGateway extends ProcessObservable {
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
