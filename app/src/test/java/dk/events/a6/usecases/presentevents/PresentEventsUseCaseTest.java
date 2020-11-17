package dk.events.a6.usecases.presentevents;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dk.events.a6.Context;
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

import static org.junit.Assert.assertEquals;

public class PresentEventsUseCaseTest {

    public static final String TITLE = "event title";
    public static final String START_DATE = "InTwoDays :)";
    private User user;
    private Event event;
    private PresentEventsUseCase useCase;

    @Before
    public void beforeAll(){
        Context.eventGateway = new EventGatewayInMemory();
        useCase = new PresentEventsUseCaseInMemory();

        user = Context.eventGateway.createUser( User.newUserBuilder().build() );
        event = Context.eventGateway.createEvent( Event.newBuilder().build() );
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

    @Test
    public void givenOneEvent_returnPresentOneEvent(){
        event.setTitle(TITLE);
        event.setStartDate(START_DATE);

        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        assertEquals(1, presentableEvents.size());

        PresentableEvent presentableEvent = presentableEvents.get(0);
        assertEquals(TITLE, presentableEvent.title);
        assertEquals(START_DATE, presentableEvent.startDate);
    }

    @Test
    public void givenNoLicenseForEvent_returnEventCannotBeParticipated(){
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(false, presentableEvent.hasLicenseFor);
    }

    @Test
    public void givenHasLicenseForEvent_returnUserHasLicenseFor(){
        Context.eventGateway.createLicense(License.newBuilder().withUser(user).withEvent(event).build());
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(true, presentableEvent.hasLicenseFor);

    }
}
