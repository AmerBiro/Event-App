package dk.events.a6.usecases;

import java.util.List;

import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public interface LicenseGateway {
    License createLicense(License license);
    List<License> findLicensesForUserAndEvent(User user, Event event);
}
