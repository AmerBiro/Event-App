package dk.eventslib.usecases.presentevents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dk.eventslib.usecases.LicenseGateway;
import dk.eventslib.usecases.createevent.ObservableEventGateway;
import dk.eventslib.entities.Event;
import dk.eventslib.entities.License;
import dk.eventslib.entities.License.LicenseType;
import dk.eventslib.entities.User;

import static dk.eventslib.entities.License.LicenseType.*;

public class PresentEventsUseCaseImpl implements PresentEventsUseCase {
    private ObservableEventGateway observableEventGateway;
    private LicenseGateway licenseGateway;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PresentEventsUseCaseImpl(ObservableEventGateway observableEventGateway, LicenseGateway licenseGateway) {
        this.observableEventGateway = observableEventGateway;
        this.licenseGateway = licenseGateway;
    }

    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        List<Event> allEvents = observableEventGateway.findAllEvents();
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
