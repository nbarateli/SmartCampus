package test;

import misc.Utils;
import model.accounts.User;
import model.accounts.User.UserRole;
import model.bookings.Booking;
import model.bookings.Booking.WeekDay;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;
import model.rooms.RoomProblem;
import model.subjects.CampusSubject;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void exactDateToStringTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, Calendar.JULY, 16, 10, 20);
        java.util.Date date = cal.getTime();
        assertEquals("16.07.2017 10:20", Utils.exactDateToString(date));

        cal.set(2017, Calendar.JULY, 16, 23, 20, 50);
        date = cal.getTime();
        assertEquals("16.07.2017 23:20", Utils.exactDateToString(date));
    }

    @Test
    public void toHHMMTest() {
        @SuppressWarnings("deprecation")
        Time time = new Time(22, 58, 40);
        assertEquals("22:58", Utils.toHHMM(time));
    }

    @Test
    public void toSqlTimeTest() {
        @SuppressWarnings("deprecation")
        Time time = new Time(22, 58, 40);
        assertEquals("STR_TO_DATE('22:58', '%H:%i')", Utils.toSqlTime(time));
    }

    @Test
    public void roomTypeToStringTest() {
        RoomType type = RoomType.valueOf("UTILITY");
        assertEquals("სხვა", Utils.roomTypeToString(type, true));
        type = RoomType.valueOf("AUDITORIUM");
        assertEquals("აუდიტორია", Utils.roomTypeToString(type, true));
    }

    @Test
    public void seatTypeToStringTest() {
        SeatType type = SeatType.valueOf("DESKS");
        assertEquals("სკამები და მერხები", Utils.seatTypeToString(type, true));
        type = SeatType.valueOf("TABLES");
        assertEquals("მაგიდები", Utils.seatTypeToString(type, true));
        type = SeatType.valueOf("COMPUTERS");
        assertEquals("კომპიუტერები", Utils.seatTypeToString(type, true));
        type = SeatType.valueOf("CHAIRS");
        assertEquals("სკამები", Utils.seatTypeToString(type, true));
    }

    @Test
    public void toRoomTypeTest() {
        RoomType type = RoomType.valueOf("UTILITY");
        assertEquals(type, Utils.toRoomType("utility"));
        type = RoomType.valueOf("AUDITORIUM");
        assertEquals(type, Utils.toRoomType("auditorium"));
        assertEquals(null, Utils.toRoomType("any"));
    }

    @Test
    public void toUserRoleTest() {
        UserRole role = UserRole.valueOf("STUDENT");
        assertEquals(role, Utils.toUserRole("student"));
        role = UserRole.valueOf("LECTURER");
        assertEquals(role, Utils.toUserRole("lecturer"));
        role = UserRole.valueOf("STAFF");
        assertEquals(role, Utils.toUserRole("staff"));
        assertEquals(null, Utils.toUserRole("any"));
    }


    @Test
    public void toSeatTypeTest() {
        SeatType type = SeatType.valueOf("DESKS");
        assertEquals(type, Utils.toSeatType("desks"));
        type = SeatType.valueOf("TABLES");
        assertEquals(type, Utils.toSeatType("tables"));
        type = SeatType.valueOf("COMPUTERS");
        assertEquals(type, Utils.toSeatType("computers"));
        type = SeatType.valueOf("CHAIRS");
        assertEquals(type, Utils.toSeatType("chairs"));
        assertEquals(null, Utils.toSeatType("any"));
    }

    @Test
    public void toWeekDayTest() {
        WeekDay day = WeekDay.valueOf("MONDAY");
        assertEquals(day, Utils.toWeekDay("monday"));
        day = WeekDay.valueOf("TUESDAY");
        assertEquals(day, Utils.toWeekDay("tuesday"));
        day = WeekDay.valueOf("WEDNESDAY");
        assertEquals(day, Utils.toWeekDay("wednesday"));
        day = WeekDay.valueOf("THURSDAY");
        assertEquals(day, Utils.toWeekDay("thursday"));
        day = WeekDay.valueOf("FRIDAY");
        assertEquals(day, Utils.toWeekDay("friday"));
        day = WeekDay.valueOf("SATURDAY");
        assertEquals(day, Utils.toWeekDay("saturday"));
        day = WeekDay.valueOf("SUNDAY");
        assertEquals(day, Utils.toWeekDay("sunday"));
    }

    @Test
    public void toGeorgianTest() {
        WeekDay day = WeekDay.valueOf("MONDAY");
        assertEquals("ორშაბათი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("TUESDAY");
        assertEquals("სამშაბათი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("WEDNESDAY");
        assertEquals("ოთხშაბათი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("THURSDAY");
        assertEquals("ხუთშაბათი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("FRIDAY");
        assertEquals("პარასკევი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("SATURDAY");
        assertEquals("შაბათი", Utils.toGeorgian(day));
        day = WeekDay.valueOf("SUNDAY");
        assertEquals("კვირა", Utils.toGeorgian(day));
    }

    @Test
    public void getUserFromResultsTest() {
        try {
            assertEquals(new User(5, "name", "name", "name", UserRole.STUDENT, ""),
                    Utils.getUserFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProblemFromResults() {
        try {
            assertEquals(new RoomProblem(5,
                            new User(5, "name", "name", "name", UserRole.STUDENT, ""),
                            new Room(5, 5, "name", RoomType.valueOf("AUDITORIUM"),
                                    SeatType.valueOf("DESKS"), false, 5), "name", "name",
                            java.sql.Date.valueOf("2017-07-16")),
                    Utils.getProblemFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWeekDayTest(){
        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strdate1 = "16-07-2017 11:35:42";
        String strdate2 = "17-07-2017 11:35:42";
        String strdate3 = "18-07-2017 11:35:42";
        String strdate4 = "19-07-2017 11:35:42";
        String strdate5 = "20-07-2017 11:35:42";
        String strdate6 = "21-07-2017 11:35:42";
        String strdate7 = "22-07-2017 11:35:42";
        try {
            Date date1;
            date1 = dateformat2.parse(strdate1);
            Date date2;
            date2 = dateformat2.parse(strdate2);
            Date date3;
            date3 = dateformat2.parse(strdate3);
            Date date4;
            date4 = dateformat2.parse(strdate4);
            Date date5;
            date5 = dateformat2.parse(strdate5);
            Date date6;
            date6 = dateformat2.parse(strdate6);
            Date date7;
            date7 = dateformat2.parse(strdate7);
            assertEquals(Utils.getWeekDay(date1), 1);
            assertEquals(Utils.getWeekDay(date2), 2);
            assertEquals(Utils.getWeekDay(date3), 3);
            assertEquals(Utils.getWeekDay(date4), 4);
            assertEquals(Utils.getWeekDay(date5), 5);
            assertEquals(Utils.getWeekDay(date6), 6);
            assertEquals(Utils.getWeekDay(date7), 7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBookingFromResultsTest(){
        try {
            assertEquals(new Booking(5, new User(5, "name", "name", "name",
                    UserRole.STUDENT, ""),
                    new Room(5, 5, "name", RoomType.valueOf("AUDITORIUM"),
                            SeatType.valueOf("DESKS"), false, 5),
                            new CampusSubject(-1, "name"),
                    new Time(4, 0, 0), new Time(4, 0, 0),
                            "new subject", java.sql.Date.valueOf("2017-07-16")),
                    Utils.getBookingFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubjectFromResults(){
        try {
            assertEquals(new CampusSubject(5, "magaltman"),
                    Utils.getSubjectFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
