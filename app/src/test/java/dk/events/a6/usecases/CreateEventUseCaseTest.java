package dk.events.a6.usecases;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dk.events.a6.usecases.entities.Event;

import static org.junit.Assert.assertEquals;

public class CreateEventUseCaseTest {

    @Test
    public void givenCreateEventRequested_returnEventCreatedByCallingEventGateway(){
        //setup
        EventGateway eventGateway = new FakeEventGateway();
        CreateEventUseCase useCase = new CreateEventUseCase();
        useCase.setEventGateway(eventGateway);
        useCase.setOutputPort(new FakeOutputPort());

        String id = UUID.randomUUID().toString();
        String title = "Is med Mia";
        Event event = Event.newBuilder()
                .withId(id)
                .withTitle(title)
                .build();

        //act
        useCase.createEvent(event);

        //assert
        Event returnedEvent = useCase.getEvent(id);
        assertEquals(event.toString(), returnedEvent.toString());
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

    static class FakeOutputPort implements CreateEventOutputPort{
        public String sendMsg = "not set";
        @Override
        public void show(String msg) {
            sendMsg = msg;
        }
    }
}
