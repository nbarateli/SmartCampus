package model.bookings;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.accounts.User;
import model.campus.CampusObject;
import model.lectures.CampusSubject;
import model.rooms.Room;

/**
 * Class for Booking ADT (immutable)
 */
public class Booking implements CampusObject{

    private final int bookingID;
    private final User booker;
    private final Room room;
    private final CampusSubject subject;
    private final WeekDay day;
    private final Time startTime;
    private final Time endTime;
    private final Date bookingDate;
    private final String description;

    public Booking(int bookingID, User booker, Room room, CampusSubject subject, WeekDay day, 
            Time startTime, Time endTime, String description, Date bookingDate) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.room = room;
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.bookingDate = bookingDate;
    }

    @Override
    public int getId() {
        return bookingID;
    }


    public User getBooker() {
        return booker;
    }

    public Room getRoom() {
        return room;
    }

    public CampusSubject getSubject() {
        return subject;
    }

    public Time getEndTime() {
        return new Time(endTime.getTime());
    }

    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    public WeekDay getDay() {
        return day;
    }
    
    public Date getDate() { return new Date(bookingDate.getTime()); }
    
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
        if (bookingID != other.bookingID)
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        return "Booking{" + "\n"
                + (subject == null ? description : subject.getName()) 
                + ": დაჯავშნა: " + booker.getFirstName() + " " +
                booker.getLastName() + " თარიღი: " + dFormat.format(bookingDate) + 
                " დღე: " + day + " დასაწყისი " + format.format(startTime) +
                " დასასრული " + format.format(endTime) +
                " აუდიტორია " + room.getRoomName() +
                "}";
    }

    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
