package dk.events.a6.usecases;

import dk.events.a6.usecases.entities.User;

public interface UserGateway {
    User createUser(User user);
    User findUser(String userName);
}
