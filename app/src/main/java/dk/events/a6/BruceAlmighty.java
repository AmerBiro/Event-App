package dk.events.a6;

import dk.events.a6.usecases.entities.User;

public class BruceAlmighty {
    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
