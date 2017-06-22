package test;

import model.accounts.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;

    @Before
    public void createUser() {
        user = new User(-1, "mail", "firstName", "lastName", null);
    }

    @Test
    public void test() {
        assertEquals(-1, user.getID());
        assertEquals("mail", user.geteMail());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());

    }

}
