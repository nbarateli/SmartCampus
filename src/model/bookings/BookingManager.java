package model.bookings;

import model.campus.CampusManager;

/**
 * The booking manager DAO.
 * Responsible for pulling and pushing information related to bookings
 * from and into the database.
 */

public interface BookingManager extends CampusManager<Booking, BookingSearchQueryGenerator>{
    
    /**
     * removes booking with given id from database
     * @param bookingId id of the booking needed to be removed
     */
    void deleteThisOccurrence(int bookingId);
    
    /**
     * removes all the occurrence of the lecture associated with this booking  
     * @param bookingId id of the booking (lecture) we should remove occurrences of
     */
    void deleteAllOccurrences(int bookingId);
}
