package dk.events.a6.usecases.presentevents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dk.events.a6.android.Context;
import dk.events.a6.usecases.LicenseGateway;
import dk.events.a6.usecases.createevent.EventGateway;
import dk.events.entities.Event;
import dk.events.entities.License;
import dk.events.entities.License.LicenseType;
import dk.events.entities.User;

import static dk.events.entities.License.LicenseType.*;

public class PresentEventsUseCaseImpl implements PresentEventsUseCase {
    private EventGateway eventGateway;
    private LicenseGateway licenseGateway;

    public PresentEventsUseCaseImpl(EventGateway eventGateway, LicenseGateway licenseGateway) {
        this.eventGateway = eventGateway;
        this.licenseGateway = licenseGateway;
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        List<Event> allEvents = eventGateway.findAllEvents();
        List<PresentableEvent> presentableEvents = new ArrayList<>();

        for (Event e: allEvents){
            presentableEvents.add(makeEventPresentable(loggedUser, e));
        }

        return presentableEvents;
    }

    private PresentableEvent makeEventPresentable(User loggedUser, Event e) {
        PresentableEvent pEvent = new PresentableEvent();

        pEvent.isParticipable = hasLicenseFor(PARTICIPATING, loggedUser, e);
        pEvent.isViewable = hasLicenseFor(VIEWING, loggedUser, e);
        pEvent.title = e.getTitle();
        pEvent.startDate = simpleDateFormat.format( e.getStartDate() );
        return pEvent;
    }

    @Override
    public boolean hasLicenseFor(LicenseType licenseType, User user, Event event) {
        List<License> licenses = licenseGateway.findLicensesForUserAndEvent(user, event);
        for (License l : licenses) {
            LicenseType type = l.getLicenseType();
            if (type == licenseType) {
                return true;
            }
        }
        return false;
    }

}
