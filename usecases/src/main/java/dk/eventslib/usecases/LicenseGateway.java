package dk.eventslib.usecases;


import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.entities.License;
import dk.eventslib.entities.User;

public interface LicenseGateway {
    License createLicense(License license);
    List<License> findLicensesForUserAndEvent(User user, Event event);
}
