package test;

import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import model.accounts.UserProblem;

import static org.junit.Assert.*;

public class UserProblemTest {

    private UserProblem problem;
    
    @Before 
    public void createUserProblem() {
        problem = new UserProblem(1, 2, 3, "title", "message", new Date(500));
    }
    
    @Test
    public void test() {
        assertEquals(1, problem.getID());
        assertEquals(2, problem.getUserID());
        assertEquals(3, problem.getWarnerID());
        assertEquals("title", problem.getTitle());
        assertEquals("message", problem.getMessage());
        assertEquals(500, problem.getDateWarned().getTime());
    }
}
