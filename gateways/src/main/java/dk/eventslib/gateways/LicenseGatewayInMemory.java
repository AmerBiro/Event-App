package dk.eventslib.gateways;

import java.util.ArrayList;
import java.util.List;

import dk.eventslib.entities.Event;
import dk.eventslib.entities.License;
import dk.eventslib.entities.User;
import dk.eventslib.usecases.LicenseGateway;

public class LicenseGatewayInMemory extends BaseGatewayInMemory<License> implements LicenseGateway {

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
