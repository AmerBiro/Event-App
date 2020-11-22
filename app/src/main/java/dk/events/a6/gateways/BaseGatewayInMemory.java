package dk.events.a6.gateways;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dk.eventslib.entities.Entity;


public class BaseGatewayInMemory <T extends Entity>{
    protected Map<String, T> usersMap = new HashMap<>(); //static?
    protected Map<String, T> eventsMap = new HashMap<>(); //static?
    protected Map<String, T> licensesMap = new HashMap<>(); //static?

    protected T setWithId(T entity) {
        if(entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());
        return entity;
    }
}
