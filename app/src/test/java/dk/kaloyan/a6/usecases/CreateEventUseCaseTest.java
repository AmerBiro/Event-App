package dk.kaloyan.a6.usecases;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dk.kaloyan.a6.gateways.EventGatewayInMemory;
import dk.kaloyan.a6.usecases.entities.Event;

import static org.junit.Assert.assertEquals;

public class CreateEventUseCaseTest {

    @Test
    public void givenCreateEventRequested_returnEventCreatedByCallingEventGateway(){
        //setup
        EventGateway eventGateway = new FakeEventGateway();
        CreateEventUseCase useCase = new CreateEventUseCase();
        useCase.setEventGateway(eventGateway);
        Event event = new Event();
        String id = UUID.randomUUID().toString();
        event.setId(id);
        String title = "Is med Mia";
        event.setTitle(title);

        //act
        useCase.createEvent(event);

        //assert
        Event returnedEvent = useCase.getEvent(id);
        assertEquals(id, returnedEvent.getId());
        assertEquals(title, returnedEvent.getTitle());
    }

    static class FakeEventGateway implements EventGateway{
        private static Map<String,Event> db = new HashMap<>();
        @Override
        public void createEvent(Event event) {
            db.put(event.getId(), event);
        }

        @Override
        public Event getEvent(String id) {
            return db.get(id);
        }
    }

}
