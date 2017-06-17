package test;

import java.sql.Time;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.accounts.User;
import model.accounts.User.UserRole;
import model.accounts.User.UserStatus;
import model.accounts.User.UserType;
import model.lecture.CampusSubject;
import model.lecture.Lecture;
import model.lecture.Lecture.WeekDay;
import model.lostandfound.ItemReport;
import model.lostandfound.ItemReport.ReportType;
import model.rooms.Room;
import model.rooms.Room.RoomType;
import model.rooms.Room.SeatType;

import static org.junit.Assert.*;

public class ItemReportTest {

    private ItemReport report;
    
    @Before 
    public void setUp() {
        report = new ItemReport(1, 1, "name", "description", ReportType.LOST, new Date(500));
    }
    
    @Test
    public void test() {
        assertEquals(1, report.getID());
        assertEquals(1, report.getAuthorID());
        assertEquals("name", report.getItemName());
        assertEquals("description", report.getItemDescription());
        assertEquals(ReportType.LOST, report.getReportType());
        assertEquals(new Date(500), report.getDateAdded());
    }

}
