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
    private PresentEventsUseCase useCase;

    @Before
    public void beforeAll(){
        Context.eventGateway = new EventGatewayInMemory();
        useCase = new PresentEventsUseCaseInMemory();

        user = Context.eventGateway.createUser( User.newUserBuilder().withUserName("userName1").build() );
        event = Event.newBuilder().build();
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnUserCannotParticipateInTheEvent(){
        assertEquals(false, useCase.isLicensedToParticipate(user, event));
    }

    @Test
    public void givenUserWithParticipationLicense_returnUserCanParticipateInTheEvent(){
        Context.eventGateway.createLicense( License.newBuilder().withUser(user).withEvent(event).build() );
        assertEquals(true, useCase.isLicensedToParticipate(user, event));
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnCannotParticipateInTheEventWhereOthersCan(){
        User otherUser = Context.eventGateway.createUser( User.newUserBuilder().withUserName("OtherUserName").build() );
        Context.eventGateway.createLicense(License.newBuilder().withUser(user).withEvent(event).build());

        assertEquals(false, useCase.isLicensedToParticipate(otherUser, event));
    }

}
