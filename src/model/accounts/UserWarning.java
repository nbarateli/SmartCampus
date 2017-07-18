package model.accounts;

import model.campus.CampusObject;

import java.util.Date;

/**
 * An user problem ADT
 */
public class UserWarning implements CampusObject {
    private final int id;
    private final int userID;
    private final int warnerID;
    private final String title;
    private final String message;
    private final long dateWarned;

    /**
     * constructor of UserWarning class
     * @param id warning id in database
     * @param userID user id in database
     * @param warnerID warner id in database
     * @param title warning title
     * @param message warning message
     * @param dateWarned warning date
     */
    public UserWarning(int id, int userID, int warnerID, String title, String message, Date dateWarned) {
        this.id = id;
        this.userID = userID;
        this.warnerID = warnerID;
        this.title = title;
        this.message = message;
        this.dateWarned = dateWarned.getTime();
    }

    /**
     * override of CampusObject's getId method (which this class is implementing)
     * @return warning id in database
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return warned user id in database
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return warner id in database
     */
    public int getWarnerID() {
        return warnerID;
    }

    /**
     * @return warning title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return warning message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return warning date
     */
    public Date getDateWarned() {
        return new Date(dateWarned);
    }

}
