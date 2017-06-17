package test;

import org.junit.Test;
import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import org.junit.Before;

import static org.junit.Assert.*;

public class UserTest {
    
    private User user;
    
    @Before 
    public void createUser() {
        user = new User(-1, "mail", "firstName", "lastName", UserStatus.valueOf("ACTIVE"),
                UserType.valueOf("USER"), UserRole.valueOf("STUDENT"));
    }
    
    @Test
    public void test() {
        assertEquals(-1, user.getID());
        assertEquals("mail", user.geteMail());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertEquals(UserType.USER, user.getUserType());
        assertEquals(UserRole.STUDENT, user.getUserRole());
    }

}
