package dk.events.a6.usecases.presentevents;

import org.junit.Before;
import org.junit.Test;

import dk.events.a6.Context;
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

import static org.junit.Assert.assertEquals;

public class PresentEventsUseCaseTest {

    private User user;
    private Event event;

    @Before
    public void beforeAll(){
        Context.eventGateway = new EventGatewayInMemory();
        user = User.newBuilder().withUserName("username").build();
        event = Event.newBuilder().build();
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnUserCannotParticipateInTheEvent(){

        PresentEventsUseCase useCase = new PresentEventsUseCaseInMemory();
        assertEquals(false, useCase.isLicensedToParticipate(user, event));
    }


    @Test
    public void givenUserWithParticipationLicense_returnUserCanParticipateInTheEvent(){
        PresentEventsUseCase useCase = new PresentEventsUseCaseInMemory();

        License participationLicense = License.newBuilder().withUser(user).withEvent(event).build();
        Context.eventGateway.createLicense(participationLicense);

        assertEquals(true, useCase.isLicensedToParticipate(user, event));


    }

}
