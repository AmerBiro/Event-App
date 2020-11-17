package dk.events.a6.usecases.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void givenTwoUsersDifferentUsers_returnUsersAreNotTheSame(){
        User user = User.newUserBuilder().withUserName("userName").build();
        User other = User.newUserBuilder().withUserName("OtherUserName").build();
        user.setId("user1_Id");
        other.setId("other_Id");

        assertEquals(false, user.isSame(other));
    }

    @Test
    public void givenTheSameUser_returnUsersIsTheSame(){
        User user = User.newUserBuilder().withUserName("userName").withId("userId").build();

        assertEquals(true, user.isSame(user));
    }

    @Test
    public void givenTwoUsersWithTheSameId_returnUsersAreTheSame(){
        final String userID = "userID";
        User user = User.newUserBuilder().withUserName("userName")
                .withId(userID).build();
        User otherUser = User.newUserBuilder().withUserName("OtherUserName")
                .withId(userID).build();


        assertEquals(true, user.isSame(otherUser));
    }

    @Test
    public void givenTwoUsersWithNullId_returnUsersAreNotTheSame(){
        User user = User.newUserBuilder().build();
        User otherUser = User.newUserBuilder().build();

        assertEquals(false, user.isSame(otherUser));
    }
}
