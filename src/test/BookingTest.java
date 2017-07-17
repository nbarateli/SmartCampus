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

public class BookingTest {

    private Booking booking;
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

        booking = new Booking(1, user, room, subject, startTime, endTime, null, date);
    }

    @Test
    public void test() {
        assertEquals(1, booking.getId());
        assertEquals(user, booking.getBooker());
        assertEquals(room, booking.getRoom());
        assertEquals(subject, booking.getSubject());
        assertEquals(date, booking.getBookingDate());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(endTime, booking.getEndTime());
        assertNull(booking.getDescription());
        assertEquals("Booking{\n" + "კალკულუსი: დაჯავშნა: firstName lastName თარიღი: 01/01/1970 " +
                "დასაწყისი 04:00 დასასრული 04:00 აუდიტორია name}",
                booking.toString());
    }

}
