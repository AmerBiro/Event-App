package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.List;

import dk.events.a6.usecases.LicenseGateway;
import dk.events.a6.usecases.entities.Event;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public class LicenseGatewayInMemory extends BaseGatewayInMemory implements LicenseGateway {

    @Override
    public List<License> findLicensesForUserAndEvent(User user, Event event) {
        List<License> returnedLicenses = new ArrayList<>();
        for (License l : licenses){
            if(l.getUser().isSame(user) && l.getEvent().isSame(event)){
                returnedLicenses.add(l);
            }
        }
        return returnedLicenses;
    }

    @Override
    public License createLicense(License license) {
        licenses.add(license);
        return license;
    }
}
