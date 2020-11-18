package dk.events.a6.usecases.presentevents;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import dk.events.a6.android.Context;
import dk.events.a6.gateways.EventGatewayInMemory;
import dk.events.a6.gateways.LicenseGatewayInMemory;
import dk.events.a6.gateways.UserGatewayInMemory;
import dk.events.a6.entities.Event;
import dk.events.a6.entities.License;
import dk.events.a6.entities.User;

import static dk.events.a6.entities.License.LicenseType.*;
import static org.junit.Assert.assertEquals;

public class PresentEventsUseCaseTest {

    public static final String TITLE = "event title";
    public static final Date START_DATE = new GregorianCalendar().getTime();
    private User user;
    private Event event;
    private PresentEventsUseCase useCase;

    @Before
    public void beforeAll(){
        Context.eventGateway = new EventGatewayInMemory();
        Context.userGateway = new UserGatewayInMemory();
        Context.licenseGateway = new LicenseGatewayInMemory();
        useCase = new PresentEventsUseCaseInMemory();

        user = Context.userGateway.createUser( User.newUserBuilder().build() );
        event = Context.eventGateway.createEvent( Event.newBuilder().withStartDate(new Date()).build() );
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnUserCannotParticipateInTheEvent(){
        assertEquals(false, useCase.hasLicenseFor(PARTICIPATING, user, event));
    }

    @Test
    public void givenUserWithParticipationLicense_returnUserCanParticipateInTheEvent(){
        Context.licenseGateway.createLicense(
                License.newBuilder().withLicenseType(PARTICIPATING).withUser(user).withEvent(event).build() );
        assertEquals(true, useCase.hasLicenseFor(PARTICIPATING, user, event));
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnCannotParticipateInTheEventWhereOthersCan(){
        User otherUser = Context.userGateway.createUser( User.newUserBuilder().withUserName("OtherUserName").build() );
        Context.licenseGateway.createLicense(
                License.newBuilder().withUser(user).withEvent(event).build());

        assertEquals(false, useCase.hasLicenseFor(PARTICIPATING, otherUser, event));
    }

    @Test
    public void givenOneEvent_returnPresentOneEvent(){
        event.setTitle(TITLE);
        event.setStartDate(START_DATE);

        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        assertEquals(1, presentableEvents.size());

        PresentableEvent presentableEvent = presentableEvents.get(0);
        assertEquals(TITLE, presentableEvent.title);
        assertEquals(new SimpleDateFormat("dd/MM/yyyy").format(START_DATE), presentableEvent.startDate);
    }

    @Test
    public void givenNoLicenseForEvent_returnEventCannotBeParticipated(){
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(false, presentableEvent.isParticipable);
    }

    @Test
    public void givenUserWithParticipationLicenseForEvent_returnUserCanParticipate(){
        Context.licenseGateway.createLicense(
                License.newBuilder().withLicenseType(PARTICIPATING).withUser(user).withEvent(event).build());
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(true, presentableEvent.isParticipable);
    }

    @Test
    public void givenUserWithViewLicenseForEvent_returnEventIsViewable(){
        Context.licenseGateway.createLicense(
                License.newBuilder().withLicenseType(VIEWING).withUser(user).withEvent(event).build() );
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(true, presentableEvent.isViewable);
        assertEquals(false, presentableEvent.isParticipable);
    }
}
