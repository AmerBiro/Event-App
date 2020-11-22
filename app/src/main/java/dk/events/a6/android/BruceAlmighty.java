package dk.events.a6.android;

import dk.eventslib.entities.User;

public class BruceAlmighty {
    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public BruceAlmighty setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        return this;
    }
}
