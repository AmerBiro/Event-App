package dk.eventslib.usecases.presentevents;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.eventslib.entities.Entity;
import dk.eventslib.usecases.LicenseGateway;
import dk.eventslib.usecases.UserGateway;
import dk.eventslib.usecases.createevent.EventGateway;
import dk.eventslib.entities.Event;
import dk.eventslib.entities.License;
import dk.eventslib.entities.User;

import static dk.eventslib.entities.License.LicenseType.*;
import static org.junit.Assert.assertEquals;

public class PresentEventsUseCaseImplTest {

    public static final String TITLE = "event title";
    public static final Date START_DATE = new GregorianCalendar().getTime();
    private User user;
    private Event event;
    private PresentEventsUseCase useCase;
    private EventGateway eventGateway;
    private UserGateway userGateway;
    private LicenseGateway licenseGateway;

    class FakeBaseGateway <T extends Entity>{
        protected Map<String, T> usersMap = new HashMap<>(); //static?
        protected Map<String, T> eventsMap = new HashMap<>(); //static?
        protected Map<String, T> licensesMap = new HashMap<>(); //static?

        protected T setWithId(T entity) {
            if(entity.getId() == null)
                entity.setId(UUID.randomUUID().toString());
            return entity;
        }
    }
    class FakeEventGateway extends FakeBaseGateway<Event> implements EventGateway{
        @Override
        public Event findEventByTitle(String title) {
            for (Event e : eventsMap.values()) {
                if(e.getTitle().equals(title)){
                    return e;
                }
            }
            return null;
        }

        public  void setEvents(List<Event> events) { //static?
            eventsMap = new HashMap<>();
            for (Event e: events)
                eventsMap.put(e.getId(),e);
        }

        @Override
        public List<Event> findAllEvents() {
            return new ArrayList<>(eventsMap.values());
        }

        @Override
        public void delete(Event event) {
            eventsMap.remove(event.getId());
        }

        @Override
        public Event createEvent(Event event) {
            eventsMap.put(event.getId(), (Event) setWithId(event));
            return event;
        }

        @Override
        public Event getEvent(String id) {
            return eventsMap.get(id);
        }

        public List<Event> getEvents() {
            return new ArrayList<>(eventsMap.values()) ;
        }
    }

    class FakeUserGateway extends FakeBaseGateway<User> implements UserGateway{
        @Override
        public User createUser(User user) {
            usersMap.put(user.getId(), (User) setWithId(user));
            return user;
        }
        @Override
        public User findUser(String userName) {
            for (User user : (List<User>) usersMap.values()) {
                if(user.getUserName().equals(userName)){
                    return user;
                }
            }
            return null;
        }
    }
    class FakeLicenseGateway extends FakeBaseGateway<License> implements LicenseGateway{
        @Override
        public List<License> findLicensesForUserAndEvent(User user, Event event) {
            List<License> returnedLicenses = new ArrayList<>();
            for (License l : licensesMap.values()){
                if(l.getUser().isSame(user) && l.getEvent().isSame(event)){
                    returnedLicenses.add(l);
                }
            }
            return returnedLicenses;
        }

        @Override
        public License createLicense(License license) {
            licensesMap.put(license.getId(), setWithId(license));
            return license;
        }
    }

    @Before
    public void beforeAll(){
        eventGateway = new FakeEventGateway();
        userGateway = new FakeUserGateway();
        licenseGateway = new FakeLicenseGateway();
        useCase = new PresentEventsUseCaseImpl(eventGateway, licenseGateway);


        user = userGateway.createUser( User.newUserBuilder().build() );
        event = eventGateway.createEvent( Event.newBuilder().withStartDate(new Date()).build() );
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnUserCannotParticipateInTheEvent(){
        assertEquals(false, useCase.hasLicenseFor(PARTICIPATING, user, event));
    }

    @Test
    public void givenUserWithParticipationLicense_returnUserCanParticipateInTheEvent(){
        licenseGateway.createLicense(
                License.newBuilder().withLicenseType(PARTICIPATING).withUser(user).withEvent(event).build() );
        assertEquals(true, useCase.hasLicenseFor(PARTICIPATING, user, event));
    }

    @Test
    public void givenUserWithoutParticipationLicense_returnCannotParticipateInTheEventWhereOthersCan(){
        User otherUser = userGateway.createUser( User.newUserBuilder().withUserName("OtherUserName").build() );
        licenseGateway.createLicense(
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
        licenseGateway.createLicense(
                License.newBuilder().withLicenseType(PARTICIPATING).withUser(user).withEvent(event).build());
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(true, presentableEvent.isParticipable);
    }

    @Test
    public void givenUserWithViewLicenseForEvent_returnEventIsViewable(){
        licenseGateway.createLicense(
                License.newBuilder().withLicenseType(VIEWING).withUser(user).withEvent(event).build() );
        List<PresentableEvent> presentableEvents =  useCase.presentEvents(user);
        PresentableEvent presentableEvent = presentableEvents.get(0);

        assertEquals(true, presentableEvent.isViewable);
        assertEquals(false, presentableEvent.isParticipable);
    }
}
