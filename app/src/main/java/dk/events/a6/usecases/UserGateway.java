package dk.events.a6.usecases;

import dk.events.entities.User;

public interface UserGateway {
    User createUser(User user);
    User findUser(String userName);
}
