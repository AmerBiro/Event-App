package dk.events.a6.usecases;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

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
        public License createLicense(License license) {

            return license;
        }

        @Override
        public List<License> findLicensesForUserAndEvent(User user, Event event) {
            return null;
        }

        @Override
        public User createUser(User user) {

            return user;
        }

        @Override
        public User findUser(String userName) {
            return null;
        }

        @Override
        public List<Event> findAllEvents() {
            return null;
        }

        @Override
        public void delete(Event event) {

        }

        @Override
        public Event findEventByTitle(String title) {
            return null;
        }

        @Override
        public Event createEvent(Event event) {
            db.put(event.getId(), event);
            return event;
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
