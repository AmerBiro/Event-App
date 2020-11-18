package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dk.events.a6.usecases.LicenseGateway;
import dk.events.a6.entities.Event;
import dk.events.a6.entities.License;
import dk.events.a6.entities.User;

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
