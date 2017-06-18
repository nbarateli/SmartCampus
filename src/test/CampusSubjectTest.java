package test;

import org.junit.Before;
import org.junit.Test;

import model.lectures.CampusSubject;

import static org.junit.Assert.*;

public class CampusSubjectTest {
    
    private CampusSubject subject;
    
    @Before 
    public void createSubject() {
        subject = new CampusSubject(1, "კალკულუსი");
    }
    
    @Test
    public void test() {
        assertEquals(1, subject.getID());
        assertEquals("კალკულუსი", subject.getName());
    }

}
