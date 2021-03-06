package test;

import model.subjects.CampusSubject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CampusSubjectTest {
    
    private CampusSubject subject1, subject2, subject3;
    
    @Before 
    public void createSubject() {
        subject1 = new CampusSubject(1, "áƒ™áƒ�áƒšáƒ™áƒ£áƒšáƒ£áƒ¡áƒ˜");
        subject2 = new CampusSubject(2, "Calculus 1");
        subject3 = new CampusSubject(2, "Calculus 1");
    }
    
    @Test
    public void test1() {
        assertEquals(1, subject1.getId());
        assertEquals("áƒ™áƒ�áƒšáƒ™áƒ£áƒšáƒ£áƒ¡áƒ˜", subject1.getName());
    }

    @Test
    public void test2() {
        assertEquals(2, subject2.getId());
        assertEquals("Calculus 1", subject2.getName());
        assertTrue(subject2.equals(subject3));
    }

}
