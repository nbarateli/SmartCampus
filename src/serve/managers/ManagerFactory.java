package serve.managers;

import model.accounts.AccountManager;
import model.bookings.BookingManager;
import model.lectures.LectureManager;
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
    private LectureManager lectureManager;
    private BookingManager bookingManager;

    public ManagerFactory() {

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

    public LectureManager getLectureManager() {
        return lectureManager == null ? lectureManager = new DefaultLectureManager(getConnector()) : lectureManager;
    }

    public BookingManager getBookingManager() {
        return roomManager == null ? bookingManager = new DefaultBookingManager(getConnector())
                : bookingManager;
    }
}
