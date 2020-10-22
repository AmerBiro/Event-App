package dk.kaloyan.a6.usecases.entities;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EventTest {

    @Test
    public void given_return(){
        //setup
        Event event = new Event();
        String title = "Is med Mia";
        String id = UUID.randomUUID().toString();

        //act
        event.setId(id);
        event.setTitle(title);

        //assert
        assertEquals(id, event.getId());
        assertEquals(title, event.getTitle());

    }
}
