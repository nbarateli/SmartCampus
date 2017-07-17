package serve.managers;

import model.accounts.AccountManager;
import model.bookings.BookingManager;
import model.subjects.SubjectManager;
import model.rooms.RoomManager;

/**
 * Created by Niko on 25.06.2017.
 * <p>
 * Responsible for creating and accessing database connector and all of the database-related
 * CampusManager implementations.
 */
public class ManagerFactory {
    private DBConnector connector;
    private RoomManager roomManager;
    private AccountManager accountManager;
    private SubjectManager subjectManager;
    private BookingManager bookingManager;

    public ManagerFactory() {
        connector = new DBConnector();
    }

    public DBConnector getConnector() {
        return connector == null ? connector = new DBConnector() : connector;
    }

    public RoomManager getRoomManager() {
        return roomManager == null ? roomManager = new DefaultRoomManager(getConnector()) : roomManager;
    }

    public AccountManager getAccountManager() {
        return accountManager == null ? accountManager = new DefaultAccountManager(getConnector()) : accountManager;
    }

    public SubjectManager getSubjectManager() {
        return subjectManager == null ? subjectManager = new DefaultSubjectManager(getConnector()) : subjectManager;
    }

    public BookingManager getBookingManager() {
        return bookingManager == null ? bookingManager = new DefaultBookingManager(getConnector())
                : bookingManager;
    }
}
