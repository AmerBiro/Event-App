package dk.events.a6.gateways;

import java.util.List;

import dk.events.a6.usecases.UserGateway;
import dk.eventslib.entities.User;

public class UserGatewayInMemory extends BaseGatewayInMemory implements UserGateway {
    @Override
    public User createUser(User user) {
        usersMap.put(user.getId(), (User) setWithId(user));
        return user;
    }
    @Override
    public User findUser(String userName) {
        for (User user : (List<User>) usersMap.values()) {
            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        return null;
    }
}
