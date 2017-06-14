package model.accounts;

import model.campus.CampusObject;

import java.util.Date;

/**
 * An user problem ADT
 */
public class UserProblem implements CampusObject {
    private final int id;
    private final int userID;
    private final int warnerID;
    private final String title;
    private final String message;
    private final long dateWarned;

    public UserProblem(int id, int userID, int warnerID, String title, String message, Date dateWarned) {
        this.id = id;
        this.userID = userID;
        this.warnerID = warnerID;
        this.title = title;
        this.message = message;
        this.dateWarned = dateWarned.getTime();
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public int getWarnerID() {
        return warnerID;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateWarned() {
        return new Date(dateWarned);
    }

    @Override
    public int getID() {
        return id;
    }
}
