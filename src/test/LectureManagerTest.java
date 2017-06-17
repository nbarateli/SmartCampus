package test;

import org.junit.Before;
import org.junit.Test;

import model.lecture.LectureManager;

import static org.junit.Assert.*;

public class LectureManagerTest {
    
    private int numLectures, numSubjects;
    private LectureManager manager;
    
    @Before
    public void setUp() {
        manager = LectureManager.getInstance();
        numLectures = manager.size();
        numSubjects = manager.numOfSubjects();
    }
    
    @Test
    public void test1() {
        manager.addSubject("a");
        assertEquals(numSubjects + 1, manager.numOfSubjects());
    }

}
