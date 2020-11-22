package dk.eventslib.gateways;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import dk.eventslib.entities.Event;

public class EventGatewayHerokuImplTest {

    private Event event;
    private EventGatewayHerokuImpl eventGatewayHeroku;

    @Before
    public void beforeAll(){
        eventGatewayHeroku = new EventGatewayHerokuImpl();

        event = Event.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withTitle("Swimming for fun")
                .withDescription("we swim for fun")
                .build();

    }

    @Test
    public void givenNewEvent_returnNewEventCreated(){
        final String TITLE = "Swimming for fun 1";
        final String DESCRIPTION = "Swimming for fun 2";
        final String ID = UUID.randomUUID().toString();
        event = Event.newBuilder()
                .withId(ID)
                .withTitle(TITLE)
                .withDescription(DESCRIPTION)
                .build();

        eventGatewayHeroku.createEvent(event);


    }

}
