package test;

import org.junit.Before;
import org.junit.Test;

import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.lectures.LectureSearchQuery;
import model.lectures.manager.*;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;

import static org.junit.Assert.*;

import java.sql.Time;

public class LectureManagerTest {
    
    private int numSubjects, numLectures;
    private LectureManager manager;
    
    @Before
    public void setUp() {
    	manager = DefaultLectureManager.getInstance();
        numSubjects = manager.numOfSubjects();
        numLectures = manager.size();
    }
    
    @Test
    public void test1() {
    	manager.addSubject("a");
        assertEquals(numSubjects + 1, manager.numOfSubjects());
        manager.removeSubject("a");
        assertEquals(numSubjects, manager.numOfSubjects());
    }
    
    @Test
    public void test2() {
        User user = new User(6, "mail", "firstName", "lastName", UserStatus.ACTIVE,
                UserType.USER, UserRole.STUDENT);
        Room room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        CampusSubject subject = new CampusSubject(1, "კალკულუსი");
        Time startTime = new Time(500);
        Time endTime = new Time(600);
        
        Lecture lecture = new Lecture(1, user, room, subject, WeekDay.MONDAY, startTime, endTime);
        
        manager.add(lecture);
        assertEquals(numLectures + 1, manager.size());
        
        int id = manager.getLectureId(lecture);
        manager.remove(id);
        assertEquals(numLectures, manager.size());
    }

}
