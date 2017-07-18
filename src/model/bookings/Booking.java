package model.bookings;

import model.accounts.User;
import model.campus.CampusObject;
import model.subjects.CampusSubject;
import model.rooms.Room;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for Booking ADT (immutable)
 */
public class Booking implements CampusObject {

    private final int bookingID;
    private final User booker;
    private final Room room;
    private final CampusSubject subject;
    private final Time startTime;
    private final Time endTime;
    private final Date bookingDate;
    private final String description;

    /**
     * constructor of Booking class
     * @param bookingID booking id in database
     * @param booker user which booked this
     * @param room room booked
     * @param subject subject booked for
     * @param startTime start time of booking
     * @param endTime end time of booking
     * @param description description of booking
     * @param bookingDate date of booking
     */
    public Booking(int bookingID, User booker, Room room, CampusSubject subject,
                   Time startTime, Time endTime, String description, Date bookingDate) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.room = room;
        this.subject = subject;
        this.startTime = new Time(startTime.getTime());
        this.endTime = new Time(endTime.getTime());
        this.description = description;
        this.bookingDate = new Date(bookingDate.getTime());

    }

    /**
     * override of CampusObject's getId method (which this class is implementing)
     * @return booking id in database
     */
    @Override
    public int getId() {
        return bookingID;
    }

    /**
     * @return booking date
     */
    public Date getBookingDate() {
        return new Date(bookingDate.getTime());
    }

    /**
     * @return booker (User)
     */
    public User getBooker() {
        return booker;
    }

    /**
     * @return room booked
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @return subject booking is for
     */
    public CampusSubject getSubject() {
        return subject;
    }

    /**
     * @return end time of booking
     */
    public Time getEndTime() {
        return new Time(endTime.getTime());
    }

    /**
     * @return start time of booking
     */
    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    /**
     * @return description of booking
     */
    public String getDescription() {
        return description;
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
        Booking other = (Booking) obj;
        return bookingID == other.bookingID;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");

        return "Booking{" + "\n"
                + (subject == null ? description : subject.getName())
                + ": დაჯავშნა: " + booker.getFirstName() + " " +
                booker.getLastName() + " თარიღი: " + dFormat.format(getBookingDate()) +
                " დასაწყისი " + format.format(startTime) +
                " დასასრული " + format.format(endTime) +
                " აუდიტორია " + room.getRoomName() +
                "}";
    }

    /**
     * enum of weekday
     */
    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
