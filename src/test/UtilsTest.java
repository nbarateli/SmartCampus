package test;

import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import org.junit.Test;

import misc.Utils;
import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import model.lecture.CampusSubject;
import model.lecture.Lecture;
import model.lecture.Lecture.WeekDay;
import model.rooms.RoomProblem;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;

import static org.junit.Assert.*;

public class UtilsTest {
    
    @Test
    public void exactDateToStringTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 6, 16, 10, 20);
        java.util.Date date = cal.getTime();
        assertEquals("16.07.2017 10:20", Utils.exactDateToString(date));
        
        cal.set(2017, 6, 16, 23, 20, 50);
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
        assertEquals("სხვა", Utils.roomTypeToString(type));
        type = RoomType.valueOf("AUDITORIUM");
        assertEquals("აუდიტორია", Utils.roomTypeToString(type));
    }
    
    @Test
    public void seatTypeToStringTest() {
        SeatType type = SeatType.valueOf("DESKS");
        assertEquals("სკამები და მერხები", Utils.seatTypeToString(type));
        type = SeatType.valueOf("TABLES");
        assertEquals("მაგიდები", Utils.seatTypeToString(type));
        type = SeatType.valueOf("COMPUTERS");
        assertEquals("კომპიუტერები", Utils.seatTypeToString(type));
        type = SeatType.valueOf("WOODEN_CHAIR");
        assertEquals("სკამ-მერხები (ხის)", Utils.seatTypeToString(type));
        type = SeatType.valueOf("PLASTIC_CHAIR");
        assertEquals("სკამ-მერხები (პლასტმასის)", Utils.seatTypeToString(type));
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
    public void toUserTypeTest() {
        UserType type = UserType.valueOf("USER");
        assertEquals(type, Utils.toUserType("user"));
        type = UserType.valueOf("ADMIN");
        assertEquals(type, Utils.toUserType("admin"));
        assertEquals(null, Utils.toUserType("any"));
    }
    
    @Test
    public void toUserStatusTest() {
        UserStatus status = UserStatus.valueOf("ACTIVE");
        assertEquals(status, Utils.toUserStatus("active"));
        status = UserStatus.valueOf("BANNED");
        assertEquals(status, Utils.toUserStatus("banned"));
        assertEquals(null, Utils.toUserStatus("any"));
    }
    
    @Test
    public void toSeatTypeTest() {
        SeatType type = SeatType.valueOf("DESKS");
        assertEquals(type, Utils.toSeatType("desks"));
        type = SeatType.valueOf("TABLES");
        assertEquals(type, Utils.toSeatType("tables"));
        type = SeatType.valueOf("COMPUTERS");
        assertEquals(type, Utils.toSeatType("computers"));
        type = SeatType.valueOf("WOODEN_CHAIR");
        assertEquals(type, Utils.toSeatType("wooden_chair"));
        type = SeatType.valueOf("PLASTIC_CHAIR");
        assertEquals(type, Utils.toSeatType("plastic_chair"));
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
            assertEquals(new User(5, "name", "name", "name", UserStatus.valueOf("ACTIVE"), 
                    UserType.valueOf("USER"), UserRole.valueOf("STUDENT")), 
                    Utils.getUserFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getProblemFromResults() {
        try {
            assertEquals(new RoomProblem(5, 
                    new User(5, "name", "name", "name", UserStatus.valueOf("ACTIVE"), 
                    UserType.valueOf("USER"), UserRole.valueOf("STUDENT")), 
                    new Room(5, 5, "name", RoomType.valueOf("AUDITORIUM"), 
                    SeatType.valueOf("DESKS"), false, 5), "name", "name", 
                    java.sql.Date.valueOf("2017-07-16")),
                    Utils.getProblemFromResults(new MockResultSet()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getLectureFromResultsTest() {
        try {
            assertEquals(new Lecture(5, 
                    new User(5, "name", "name", "name", UserStatus.valueOf("ACTIVE"), 
                    UserType.valueOf("USER"), UserRole.valueOf("STUDENT")), 
                    new Room(5, 5, "name", RoomType.valueOf("AUDITORIUM"), 
                    SeatType.valueOf("DESKS"), false, 5),
                    new CampusSubject(5, "name"), WeekDay.valueOf("MONDAY"),
                    new Time(500), new Time(500)),
                    Utils.getLectureFromResults(new MockResultSet()));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
