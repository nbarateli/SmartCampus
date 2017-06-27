package model.accounts;

import model.campus.CampusObject;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus account ADT
 * (immutable)
 */
public class User implements CampusObject {

    private final int userID;
    private final String eMail;
    private final String firstName;
    private final String lastName;
    private final UserRole initialRole;

    public User(int userID, String eMail, String firstName, String lastName,
                UserRole initialRole) {
        this.userID = userID;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initialRole = initialRole;
    }


    public UserRole getInitialRole() {
        return initialRole;
    }

    @Override

    public int getId() {
        return userID;
    }

    public String geteMail() {
        return eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * override of equals method automatically generated by eclipse
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return userID == other.userID;
    }

    public enum UserRole {
        STUDENT, LECTURER, STAFF, ADMIN, SYS_ADMIN
    }


    public enum UserPermission {
        BOOK_A_ROOM, REQUEST_BOOKED_ROOM, CANCEL_BOOKING, REPORT_ROOM_PROBLEM,
        DELETE_PROBLEM, LOST_FOUND_POST, LOST_FOUND_DELETE, WARN_USER,
        VIEW_USER_WARNINGS, REMOVE_PERMISSION, DELETE_USER_WARNINGS, INSERT_DATA
    }


}
