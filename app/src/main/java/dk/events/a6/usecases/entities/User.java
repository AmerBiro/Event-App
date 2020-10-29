package dk.events.a6.usecases.entities;

import androidx.recyclerview.widget.ItemTouchHelper;

public class User {
    private String id = "";
    private String firstName = "";
    private String lastName = "";

    private User(){

    }
    public String toString(){
        return String.format(
                "User: id = %s, firstName = %s, lastName = %s",
                id,
                firstName,
                lastName);
    }
    public static UserBuilder newBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private String id = "";
        private String firstName = "";
        private String lastName = "";
        private UserBuilder(){

        }
        public User build(){
            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            return user;
        }

        public UserBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}