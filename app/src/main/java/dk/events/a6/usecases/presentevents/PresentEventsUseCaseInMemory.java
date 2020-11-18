package dk.events.a6.usecases.presentevents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dk.events.a6.Context;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.ParticipationLicense;
import dk.events.a6.usecases.entities.User;
import dk.events.a6.usecases.entities.ViewLicense;

public class PresentEventsUseCaseInMemory implements PresentEventsUseCase {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public List<PresentableEvent> presentEvents(User loggedUser) {
        List<Event> allEvents = Context.eventGateway.findAllEvents();
        List<PresentableEvent> presentableEvents = new ArrayList<>();
        for (Event e: allEvents){
            PresentableEvent pEvent = new PresentableEvent();

            pEvent.isParticipable = hasParticipationLicense(loggedUser,e);
            pEvent.isViewable = hasViewLicense(loggedUser, e);
            pEvent.title = e.getTitle();
            pEvent.startDate = simpleDateFormat.format( e.getStartDate() );

            presentableEvents.add(pEvent);
        }
        return presentableEvents;
    }

    @Override
    public boolean hasParticipationLicense(User user, Event event) {
        List<License> licenses = Context.eventGateway.findLicensesForUserAndEvent(user, event);
        for (License l : licenses){
            if(l instanceof ParticipationLicense){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasViewLicense(User user, Event event) {
        List<License> licenses = Context.eventGateway.findLicensesForUserAndEvent(user, event);
        for (License l : licenses){
            if(l instanceof ViewLicense){
                return true;
            }
        }
        return false;
    }
}
