package dk.events.a6.usecases;


import java.util.List;

import dk.events.entities.Event;
import dk.events.entities.License;
import dk.events.entities.User;

public interface LicenseGateway {
    License createLicense(License license);
    List<License> findLicensesForUserAndEvent(User user, Event event);
}
