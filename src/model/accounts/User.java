package model.accounts;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The SmartCampus account ADT
 * (immutable)
 */
public class User {
    private final int userID;
    private final String eMail, firstName, lastName;
    private final UserStatus status;
    private final UserType userType;
    private final UserRole userRole;

    public int getUserID() {
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
