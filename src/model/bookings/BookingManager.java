package model.bookings;

import model.campus.CampusManager;

/**
 * The booking manager DAO.
 * Responsible for pulling and pushing information related to bookings
 * from and into the database.
 */

public interface BookingManager extends CampusManager<Booking, BookingSearchQueryGenerator>{
    
    /**
     * removes all the occurrence of the lecture (with same weekday and time) 
     * associated with this booking  
     * @param bookingId id of the booking (lecture) we should remove occurrences of
     */
    void deleteAllOccurrences(int bookingId);
    
    /**
     * removes all bookings from database
     */
    void removeAllBookings();
    
    /**
     * removes all lectures from database
     */
    void removeAllLectures();
}
