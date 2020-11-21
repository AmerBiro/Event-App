package dk.events.entities;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String userName;

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
    public static UserBuilder newUserBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{
        private String id;
        private String firstName;
        private String lastName;
        private String userName;

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
