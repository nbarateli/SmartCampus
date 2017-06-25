package test;

import model.accounts.User;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.lectures.Lecture.WeekDay;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class LectureTest {

    private Lecture lecture;
    private Room room;
    private CampusSubject subject;
    private User user;
    private Time startTime, endTime;

    @Before
    public void setUp() {
        user = new User(-1, "mail", "firstName", "lastName", null);
        room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        subject = new CampusSubject(1, "კალკულუსი");
        startTime = new Time(500);
        endTime = new Time(600);

        lecture = new Lecture(1, user, room, subject, WeekDay.MONDAY, startTime, endTime);
    }

    @Test
    public void test() {
        assertEquals(1, lecture.getId());
        assertEquals(user, lecture.getLecturer());
        assertEquals(room, lecture.getRoom());
        assertEquals(subject, lecture.getSubject());
        assertEquals(WeekDay.MONDAY, lecture.getDay());
        assertEquals(startTime, lecture.getStartTime());
        assertEquals(endTime, lecture.getEndTime());
        assertEquals("Lecture{\nკალკულუსი: ლექტორი: firstName lastName დღე: MONDAY " +
                "დასაწყისი 04:00 აუდიტორია name}", lecture.toString());
    }

}
