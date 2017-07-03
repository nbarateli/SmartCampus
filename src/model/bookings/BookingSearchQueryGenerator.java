package model.bookings;

import model.accounts.User;
import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;
import model.lectures.CampusSubject;
import model.lectures.Lecture;
import model.rooms.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;
import static model.database.SQLConstants.SQL_COLUMN_LECTURE_END_TIME;
import static model.database.SQLConstants.SQL_TABLE_LECTURE;

public class BookingSearchQueryGenerator implements CampusSearchQueryGenerator<Booking>{

    private Integer bookingID;
    private User booker;
    private Room room;
    private CampusSubject subject;
    private Lecture.WeekDay day;
    private Time startTime;
    private Time endTime;
    private Date bookingDate;
    private String description;

    public BookingSearchQueryGenerator(Integer bookingID, User booker, Room room, CampusSubject subject, Lecture.WeekDay day,
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

    //Default Constructor
    public BookingSearchQueryGenerator(){
        this(null, null, null, null, null,
                null, null, null, null);
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

    public void setEndTime(Time endTime) { this.endTime = endTime; }

    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Lecture.WeekDay getDay() {
        return day;
    }

    public void setDay(Lecture.WeekDay day) {
        this.day = day;
    }

    public Date getDate() {
        return bookingDate;
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
        String sql = hasNonNullFields() ? String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.%s" +
                        " JOIN %s ON %s.%s = %s.%s " +
                        " JOIN %s ON %s.%s= %s.%s" +
                        " \nWHERE %s%s%s%s%s%s%s%s",
                SQL_TABLE_BOOKING, SQL_TABLE_ROOM, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_USER, SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_BOOKER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID, SQL_TABLE_SUBJECT,
                SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_TABLE_SUBJECT,
                SQL_COLUMN_SUBJECT_ID,
                generateEqualQuery(bookingID, SQL_COLUMN_BOOKING_ID, values) + " AND ",
                generateEqualQuery(booker.getId(), SQL_COLUMN_BOOKING_BOOKER, values) + " AND ",
                generateEqualQuery(room.getId(), SQL_COLUMN_BOOKING_ROOM, values) + " AND ",
                generateLikeQuery(subject.getId(), SQL_COLUMN_BOOKING_SUBJECT_ID, values) + " AND ",
                generateLikeQuery(day, SQL_COLUMN_BOOKING_WEEK_DAY, values) + " AND ",
                generateEqualsOrQuery(startTime, SQL_COLUMN_BOOKING_START_TIME, values, true) + " AND ",
                generateEqualsOrQuery(endTime, SQL_COLUMN_BOOKING_END_TIME, values, false) + " AND ",
                generateLikeQuery(description, SQL_COLUMN_BOOKING_DESCRIPTION, values)
        ) : "SELECT * FROM " + SQL_TABLE_BOOKING;
        return new CampusSearchQuery(sql, asArray(values));
    }

    private boolean hasNonNullFields() {
        return bookingID != null || booker != null
                || room != null || subject != null
                || day != null || startTime != null
                || endTime != null || bookingDate != null
                || description != null;
    }
}
