package test;

import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import model.accounts.UserProblem;

import static org.junit.Assert.*;

public class UserProblemTest {

private UserProblem problem;
    
    @Before 
    public void createUserProblem() {
        problem = new UserProblem(1, 1, 1, "title", "message", new Date(500));
    }
    
    @Test
    public void test() {
        assertEquals(1, problem.getID());
        assertEquals(1, problem.getUserID());
        assertEquals(1, problem.getWarnerID());
        assertEquals("title", problem.getTitle());
        assertEquals("message", problem.getMessage());
        assertEquals(500, problem.getDateWarned().getTime());
    }
}
