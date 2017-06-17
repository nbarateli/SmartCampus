package model.lostandfound;

import model.campus.CampusObject;

import java.util.Date;

public class ItemReport implements CampusObject {
    private final int id;
    private final int authorID;
    private final String itemName;
    private final String itemDescription;
    private final ReportType reportType;
    private final long dateAdded;


    public ItemReport(int id, int authorID, String itemName, String itemDescription, 
            ReportType reportType, Date dateAdded) {
        this.id = id;
        this.authorID = authorID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.reportType = reportType;
        this.dateAdded = dateAdded.getTime();
    }

    @Override
    public int getID() {
        return id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public Date getDateAdded() {
        return new Date(dateAdded);
    }

    public enum ReportType {
        LOST, FOUND
    }
}
