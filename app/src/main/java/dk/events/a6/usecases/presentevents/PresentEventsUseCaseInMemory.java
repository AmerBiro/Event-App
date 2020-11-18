package dk.events.a6.usecases.presentevents;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dk.events.a6.Context;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.License.LicenseType;
import dk.events.a6.usecases.entities.User;

import static dk.events.a6.usecases.entities.License.LicenseType.*;

public class PresentEventsUseCaseInMemory implements PresentEventsUseCase {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        List<Event> allEvents = Context.eventGateway.findAllEvents();
        List<PresentableEvent> presentableEvents = new ArrayList<>();

        for (Event e: allEvents){
            presentableEvents.add(makeEventPresentable(loggedUser, e));
        }

        return presentableEvents;
    }

    @NotNull
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
        List<License> licenses = Context.eventGateway.findLicensesForUserAndEvent(user, event);
        for (License l : licenses) {
            LicenseType type = l.getLicenseType();
            if (type == licenseType) {
                return true;
            }
        }
        return false;
    }

}
