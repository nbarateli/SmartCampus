package model.bookings;

import model.campus.CampusManager;
import model.rooms.Room;

import java.util.Date;
import java.util.List;

/**
 * The booking manager DAO.
 * Responsible for pulling and pushing information related to bookings
 * from and into the database.
 */

public interface BookingManager extends CampusManager<Booking, BookingSearchQueryGenerator> {

    /**
     * removes all the occurrence of the lecture (with same weekday and time)
     * associated with this booking
     *
     * @param bookingId id of the booking (lecture) we should remove occurrences of
     * @return true if operation was successful
     */
    boolean deleteAllOccurrences(int bookingId);

    /**
     * removes all bookings from database
     */
    void removeAllBookings();

    /**
     * removes all subjects from database
     */
    void removeAllLectures();

    /**
     * Returns the list of all the bookings on passed day
     */
    List<Booking> getAllBookingsOn(Date day, Room room);
}
