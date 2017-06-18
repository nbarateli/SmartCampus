package test;

import org.junit.Before;
import org.junit.Test;

import misc.DBInfo;
import model.lectures.manager.*;

import static org.junit.Assert.*;

public class LectureManagerTest {
    
    private int numSubjects;
    private LectureManager manager;
    
    @Before
    public void setUp() {
    	manager = DefaultLectureManager.getInstance();
        numSubjects = manager.numOfSubjects();
    }
    
    @Test
    public void test1() {
    	manager.addSubject("a");
        assertEquals(numSubjects+1, manager.numOfSubjects());
    }

}
