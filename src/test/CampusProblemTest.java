package test;

import model.problems.CampusProblem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CampusProblemTest {

    private CampusProblem problem1, problem2, problem3, problem4;
    
    @Before 
    public void setUp() {
        problem1 = new CampusProblem(1, 2, 3, "t", "d");
        problem2 = new CampusProblem(11, 12, 13, "title", "desc");
        problem3 = new CampusProblem(2, 1, 1, "water", "no water on floor 3");
        problem4 = new CampusProblem(3, 9, 1, "broken chair", "broken chair in room 204");
    }
    
    @Test
    public void test1() {
        assertEquals(1, problem1.getId());
        assertEquals(2, problem1.getReporterID());
        assertEquals(3, problem1.getSolverID());
        assertEquals("t", problem1.getTitle());
        assertEquals("d", problem1.getDescription());
    }
    
    @Test
    public void test2() {
        assertEquals(11, problem2.getId());
        assertEquals(12, problem2.getReporterID());
        assertEquals(13, problem2.getSolverID());
        assertEquals("title", problem2.getTitle());
        assertEquals("desc", problem2.getDescription());
    }
    
    @Test
    public void test3() {
        assertEquals(2, problem3.getId());
        assertEquals(1, problem3.getReporterID());
        assertEquals(1, problem3.getSolverID());
        assertEquals("water", problem3.getTitle());
        assertEquals("no water on floor 3", problem3.getDescription());
    }
    
    @Test
    public void test4() {
        assertEquals(3, problem4.getId());
        assertEquals(9, problem4.getReporterID());
        assertEquals(1, problem4.getSolverID());
        assertEquals("broken chair", problem4.getTitle());
        assertEquals("broken chair in room 204", problem4.getDescription());
    }

}
