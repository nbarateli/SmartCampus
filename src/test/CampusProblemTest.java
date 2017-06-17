package test;

import org.junit.Before;
import org.junit.Test;

import model.problems.CampusProblem;

import static org.junit.Assert.*;

public class CampusProblemTest {

private CampusProblem problem, problem1;
    
    @Before 
    public void setUp() {
        problem = new CampusProblem(1, 2, 3, "t", "d");
        problem1 = new CampusProblem(11, 12, 13, "title", "desc");
    }
    
    @Test
    public void test1() {
        assertEquals(1, problem.getID());
        assertEquals(2, problem.getReporterID());
        assertEquals(3, problem.getSolverID());
        assertEquals("t", problem.getTitle());
        assertEquals("d", problem.getDescription());
    }
    
    @Test
    public void test2() {
        assertEquals(11, problem1.getID());
        assertEquals(12, problem1.getReporterID());
        assertEquals(13, problem1.getSolverID());
        assertEquals("title", problem1.getTitle());
        assertEquals("desc", problem1.getDescription());
    }

}
