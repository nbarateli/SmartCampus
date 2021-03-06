package model.accounts;

import model.campus.CampusObject;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus account ADT
 * (immutable)
 */
public class User implements CampusObject {
    private final String imageURL;

    private final int userID;
    private final String eMail;
    private final String firstName;
    private final String lastName;
    private final UserRole initialRole;

    /**
     * constructor of User class
     * @param userID user id in database
     * @param eMail user email
     * @param firstName user first name
     * @param lastName user last name
     * @param initialRole user initial role
     * @param imageURL user image url
     */
    public User(int userID, String eMail, String firstName, String lastName,
                UserRole initialRole, String imageURL) {
        this.userID = userID;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initialRole = initialRole;
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "eMail='" + eMail + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", initialRole=" + initialRole +
                '}';
    }

    /**
     * override of CampusObjects getId (which this class is implementing)
     * @return id of this user in database
     */
    @Override
    public int getId() {
        return userID;
    }

    /**
     * @return image URL of this user
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @return initial role of this user
     */
    public UserRole getInitialRole() {
        return initialRole;
    }

    /**
     * @return email of this user
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * @return first name of this user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return last name of this user
     */
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

    /**
     * enum representing user role
     */
    public enum UserRole {
        STUDENT, LECTURER, STAFF, ADMIN, SYS_ADMIN
    }

    /**
     * enum representing user permission
     */
    public enum UserPermission {
        BOOK_A_ROOM, REQUEST_BOOKED_ROOM, CANCEL_BOOKING, REPORT_ROOM_PROBLEM,
        DELETE_PROBLEM, LOST_FOUND_POST, LOST_FOUND_DELETE, WARN_USER,
        VIEW_USER_WARNINGS, REMOVE_PERMISSION, DELETE_USER_WARNINGS, INSERT_DATA
    }


}
