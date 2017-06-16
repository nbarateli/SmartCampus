package model.accounts;

import model.campus.CampusObject;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus account ADT
 * (immutable)
 */
public class User implements CampusObject {

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (eMail == null) {
            if (other.eMail != null)
                return false;
        } else if (!eMail.equals(other.eMail))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (status != other.status)
            return false;
        if (userID != other.userID)
            return false;
        if (userRole != other.userRole)
            return false;
        if (userType != other.userType)
            return false;
        return true;
    }

    private final int userID;
    private final String eMail;
    private final String firstName;
    private final String lastName;
    private final UserStatus status;
    private final UserType userType;
    private final UserRole userRole;

    public User(int userID, String eMail, String firstName, String lastName,
                UserStatus status, UserType userType, UserRole userRole) {
        this.userID = userID;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.userType = userType;
        this.userRole = userRole;
    }

    @Override
    public int getID() {
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

    public UserStatus getStatus() {
        return status;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public enum UserStatus {
        ACTIVE, BANNED
    }

    public enum UserRole {
        STUDENT, LECTURER, STAFF
    }

    public enum UserType {
        ADMIN, USER;

        @Override
        public String toString() {
            switch (this) {
                case USER:
                    return "User";
                case ADMIN:
                    return "Administrator";
            }
            return "";
        }
    }
}
