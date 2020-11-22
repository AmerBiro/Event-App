package dk.eventslib.gateways;

import java.util.List;

import dk.eventslib.entities.User;
import dk.eventslib.usecases.UserGateway;

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
