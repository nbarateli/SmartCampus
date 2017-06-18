package test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.lostandfound.ItemReport;
import model.lostandfound.ItemReport.ReportType;

import static org.junit.Assert.*;

public class ItemReportTest {

    private ItemReport report;
    
    @Before 
    public void setUp() {
        report = new ItemReport(1, 2, "name", "description", ReportType.LOST, new Date(500));
    }
    
    @Test
    public void test() {
        assertEquals(1, report.getID());
        assertEquals(2, report.getAuthorID());
        assertEquals("name", report.getItemName());
        assertEquals("description", report.getItemDescription());
        assertEquals(ReportType.LOST, report.getReportType());
        assertEquals(new Date(500), report.getDateAdded());
    }

}
