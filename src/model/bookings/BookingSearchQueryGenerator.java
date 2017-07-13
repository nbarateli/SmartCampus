package model.bookings;

import model.accounts.User;
import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;
import model.lectures.CampusSubject;
import model.rooms.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

public class BookingSearchQueryGenerator implements CampusSearchQueryGenerator<Booking> {

    private Integer bookingID;
    private User booker;
    private Room room;
    private CampusSubject subject;
    private Booking.WeekDay day;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private String description;

    public BookingSearchQueryGenerator(Integer bookingID, User booker, Room room, CampusSubject subject, Booking.WeekDay day,
                                       Time startTime, Time endTime, String description, Date startDate, Date endDate) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.room = room;
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Default Constructor
    public BookingSearchQueryGenerator() {
        this(null, null, null, null, null,
                null, null, null, null, null);
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate == null ? null : new Date(endDate.getTime());

    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(Date endDate) {
        this.startDate = endDate == null ? null : new Date(endDate.getTime());
    }

    public int getId() {
        return bookingID;
    }

    public void setId(int bookingID) {
        this.bookingID = bookingID;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public CampusSubject getSubject() {
        return subject;
    }

    public void setSubject(CampusSubject subject) {
        this.subject = subject;
    }

    public Time getEndTime() {
        return new Time(endTime.getTime());
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Booking.WeekDay getDay() {
        return day;
    }

    public void setDay(Booking.WeekDay day) {
        this.day = day;
    }

    public Date getDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public CampusSearchQuery generateQuery() {
        List<Object> values = new ArrayList<>();
        String sql = hasNonNullFields() ? String.format("SELECT * FROM \n%s \nJOIN %s ON %s.%s = %s.%s\n" +
                        " JOIN %s ON %s.%s = %s.%s \n" +
                        joinSubject() +
                        " \nWHERE \n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
                SQL_TABLE_BOOKING, SQL_TABLE_ROOM, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_USER, SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_BOOKER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID,
                generateEqualQuery(bookingID, SQL_COLUMN_BOOKING_ID, values) + " AND ",
                generateEqualQuery(booker, SQL_COLUMN_BOOKING_BOOKER, values) + " AND ",
                generateEqualQuery(room == null ? null : room.getId(),
                        SQL_TABLE_ROOM + "." + SQL_COLUMN_BOOKING_ROOM, values) + " AND ",
                generateLikeQuery(subject, SQL_COLUMN_BOOKING_SUBJECT_ID, values) + " AND ",
                generateLikeQuery(day, SQL_COLUMN_BOOKING_WEEK_DAY, values) + " AND ",
                generateTimeQuery(endTime, SQL_COLUMN_BOOKING_START_TIME, values, false) + " AND ",
                generateTimeQuery(startTime, SQL_COLUMN_BOOKING_END_TIME, values, true) + " AND ",
                generateLikeQuery(description, SQL_COLUMN_BOOKING_DESCRIPTION, values) + " AND ",
                generateDateQuery(startDate, SQL_COLUMN_BOOKING_START_DATE, values, true) + "AND",
                generateDateQuery(endDate, SQL_COLUMN_BOOKING_END_DATE, values, false)
        ) : "SELECT * FROM " + SQL_TABLE_BOOKING;
        return new CampusSearchQuery(sql, asArray(values));
    }

    private String joinSubject() {
        return String.format(" LEFT JOIN %s ON %s.%s = %s.%s", SQL_TABLE_SUBJECT, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_TABLE_SUBJECT, SQL_COLUMN_SUBJECT_ID);
    }

    private boolean hasNonNullFields() {
        return bookingID != null || booker != null
                || room != null || subject != null
                || day != null || startTime != null
                || endTime != null || startDate != null
                || description != null;
    }
}
