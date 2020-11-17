package dk.events.a6.usecases.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void givenTwoUsersDifferentUsers_returnUsersAreNotTheSame(){
        User user = User.newBuilder().withUserName("userName").build();
        User other = User.newBuilder().withUserName("OtherUserName").build();
        assertEquals(false, user.isSame(other));
    }

    @Test
    public void givenTheSameUser_returnUsersIsTheSame(){
        User user = User.newBuilder().withUserName("userName").build();
        assertEquals(true, user.isSame(user));
    }
}
