package dk.events.a6.usecases.entities;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.util.Objects;

public class User {
    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String userName = "";

    private User(){

    }
    public String toString(){
        return String.format(
                "User: id = %s, firstName = %s, lastName = %s, userName = %s",
                id,
                firstName,
                lastName,
                userName);
    }
    public static UserBuilder newBuilder(){
        return new UserBuilder();
    }

    public boolean isSame(User user) {
        return Objects.equals(id, user.id);
    }

    public static class UserBuilder{
        private String id = "";
        private String firstName = "";
        private String lastName = "";
        private String userName = "";

        private UserBuilder(){

        }
        public User build(){
            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserName(userName);
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

        public UserBuilder withUserName(String userName) {
            this.userName = userName;
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
    private void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
}
