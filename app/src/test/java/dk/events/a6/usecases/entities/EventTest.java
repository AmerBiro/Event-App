package dk.events.a6.usecases.entities;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EventTest {

    @Test
    public void given_return(){
        //setup
        String title = "Is med Mia";
        String id = UUID.randomUUID().toString();

        Event event = Event.newBuilder()
                .withId(id)
                .withTitle(title)
                .withOwner(User.newBuilder().withId(UUID.randomUUID().toString()).withFirstName("Bob").withLastName("Marley").build())
                .build();

        //act
        event.setId(id);
        event.setTitle(title);

        //assert
        assertEquals(id, event.getId());
        assertEquals(title, event.getTitle());

    }
}
