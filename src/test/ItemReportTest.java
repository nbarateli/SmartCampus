package test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.lostandfound.ItemReport;
import model.lostandfound.ItemReport.ReportType;

import static org.junit.Assert.*;

public class ItemReportTest {

    private ItemReport report1, report2;
    
    @Before 
    public void setUp() {
        report1 = new ItemReport(1, 2, "name", "description", ReportType.LOST, new Date(500));
        report2 = new ItemReport(4, 7, "book", "Cracking the Coding Interview", ReportType.FOUND, new Date(500));
    }
    
    @Test
    public void test1() {
        assertEquals(1, report1.getID());
        assertEquals(2, report1.getAuthorID());
        assertEquals("name", report1.getItemName());
        assertEquals("description", report1.getItemDescription());
        assertEquals(ReportType.LOST, report1.getReportType());
        assertEquals(new Date(500), report1.getDateAdded());
    }
    
    @Test
    public void test2() {
        assertEquals(4, report2.getID());
        assertEquals(7, report2.getAuthorID());
        assertEquals("book", report2.getItemName());
        assertEquals("Cracking the Coding Interview", report2.getItemDescription());
        assertEquals(ReportType.FOUND, report2.getReportType());
        assertEquals(new Date(500), report2.getDateAdded());
    }

}
