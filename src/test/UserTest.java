package test;

import model.accounts.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private User user1, user2;

    @Before
    public void createUser() {
        user1 = new User(-1, "mail", "firstName", "lastName", User.UserRole.STAFF, "");
        user2 = user1;
    }

    @Test
    public void test() {
        assertEquals(-1, user1.getId());
        assertEquals("mail", user1.geteMail());
        assertEquals("firstName", user1.getFirstName());
        assertEquals("lastName", user1.getLastName());
        assertEquals(User.UserRole.STAFF, user1.getInitialRole());
        assertTrue(user1.equals(user2));
    }

}
