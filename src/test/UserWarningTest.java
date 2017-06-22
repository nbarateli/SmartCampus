package test;

import model.accounts.UserWarning;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserWarningTest {

    private UserWarning problem;
    
    @Before 
    public void createUserProblem() {
        problem = new UserWarning(1, 2, 3, "title", "message", new Date(500));
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
