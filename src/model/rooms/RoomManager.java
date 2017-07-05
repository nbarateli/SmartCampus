package model.rooms;

import model.bookings.Booking;
import model.bookings.Booking.WeekDay;
import model.campus.CampusManager;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Niko on 07.06.2017.
 * <p>
 * The room manager DAO.
 * Responsible for pulling and pushing information related to user accounts
 * from and into the database.
 */
public interface RoomManager extends CampusManager<Room, RoomSearchQueryGenerator> {

    /**
     * Returns a list of URL-s of all the images of given room.
     *
     * @return a list of <code>String</code> URL-s
     */
    List<String> getAllImagesOf(Room room);

    /**
     * Returns the list of all bookings that take place in the room
     *
     * @param room a room where the bookings take place
     */
    List<Booking> findAllBookingsAt(Room room);

    /**
     * Returns a list of all the bookings that take place in the room
     * at given day.
     *
     * @param room a room where the bookings take place
     * @param day  the day on which the bookings take place
     */
    List<Booking> findAllBookingsAt(Room room, WeekDay day);

    /**
     * Returns a list of all the bookings that take place in the room
     * at given day and start within the range of start and end times.
     *
     * @param room  a room where the bookings take place
     * @param day   the day on which the bookings take place
     * @param start start of the time range when the bookings start
     * @param end   end of the time range when the bookings start
     */
    List<Booking> findAllBookingsAt(Room room, WeekDay day, Time start, Time end);

    /**
     * Returns a booking that takes place in the room
     * at given day and on given time
     *
     * @param room    a room where the booking takes place
     * @param date    the date on which the booking takes place
     * @param current time we should check for the booking
     */
    Booking findBookingAt(Room room, Date date, Time current);

    /**
     * Tells if there is a booking that takes place in the room
     * at given date and on given current time.
     *
     * @param room    a room we are interested in
     * @param date    the date we are interested in
     * @param current time we are interested in
     */
    boolean isBooked(Room room, Date date, Time current);

    /**
     * returns room with given id from the database
     *
     * @param id id of the room we're searching for
     */
    Room getRoomById(int id);

    /**
     * returns room with given name from the database
     *
     * @param roomName the name of the room to be found
     */
    Room getRoomByName(String roomName);

    /**
     * Returns the list of all the infrastructural problems this room currently has.
     *
     * @return a <code>List</code> of <code>RoomProblem</code> s
     */
    List<RoomProblem> findAllProblemsOf(Room room);

    /**
     * Adds new image for the room object in the database
     *
     * @param room     a room to which said image belongs to
     * @param imageURL an URL to the image
     */
    void addImage(Room room, String imageURL);

    /**
     * Returns a list of names of all the rooms.
     */
    List<String> getAllRoomNames();
}
