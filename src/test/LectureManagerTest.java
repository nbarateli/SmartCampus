package test;

import misc.DBInfo;
import model.accounts.User;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.lectures.LectureManager;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import org.junit.Before;
import org.junit.Test;
import serve.managers.ManagerFactory;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class LectureManagerTest {

    private int numSubjects, numLectures;
    private LectureManager manager;

    @Before
    public void setUp() throws Exception {
        DBInfo.class.newInstance();
        manager = new ManagerFactory().getLectureManager();
        numSubjects = manager.numOfSubjects();
//TODO        numLectures = manager.size();
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
        User user = new User(6, "mail", "firstName", "lastName", null, "");
        Room room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        CampusSubject subject = new CampusSubject(1, "კალკულუსი");
        Time startTime = new Time(500);
        Time endTime = new Time(600);

        Lecture lecture = new Lecture(1, user, room, subject, WeekDay.MONDAY, startTime, endTime);

        manager.add(lecture);
//    TODO    assertEquals(numLectures + 1, manager.size());

        int id = manager.getLectureId(lecture);
        manager.remove(id);
//       TODO  assertEquals(numLectures, manager.size());
    }

}
