package dk.events.a6.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dk.events.a6.usecases.entities.Entity;
import dk.events.a6.usecases.entities.License;
import dk.events.a6.usecases.entities.User;

public class BaseGatewayInMemory {
    protected Map<String, User> usersMap = new HashMap<>(); //static?
    protected List<License> licenses = new ArrayList<>(); //static?

    protected Entity setWithId(Entity entity) {
        if(entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());
        return entity;
    }

}
