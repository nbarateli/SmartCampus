package test;

import model.accounts.User;
import model.bookings.Booking;
import model.subjects.CampusSubject;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BookingTest {

    private Booking booking1;
    private Booking booking2;
    private Room room;
    private CampusSubject subject;
    private User user;
    private Date date;
    private Time startTime, endTime;

    @Before
    public void setUp() {
        user = new User(-1, "mail", "firstName", "lastName", null, "");
        room = new Room(5, 5, "name", RoomType.AUDITORIUM, SeatType.DESKS, false, 5);
        subject = new CampusSubject(1, "კალკულუსი");
        date = new Date(1500);
        startTime = new Time(500);
        endTime = new Time(600);

        booking1 = new Booking(1, user, room, subject, startTime, endTime, null, date);
        booking2 = new Booking(1, user, room, subject, startTime, endTime, null, date);
    }

    @Test
    public void test() {
        assertEquals(1, booking1.getId());
        assertEquals(user, booking1.getBooker());
        assertEquals(room, booking1.getRoom());
        assertEquals(subject, booking1.getSubject());
        assertEquals(date, booking1.getBookingDate());
        assertEquals(startTime, booking1.getStartTime());
        assertEquals(endTime, booking1.getEndTime());
        assertNull(booking1.getDescription());
        assertEquals("Booking{\n" + "კალკულუსი: დაჯავშნა: firstName lastName თარიღი: 01/01/1970 " +
                "დასაწყისი 04:00 დასასრული 04:00 აუდიტორია name}",
                booking1.toString());
        assertTrue(booking1.equals(booking2));
    }

}
