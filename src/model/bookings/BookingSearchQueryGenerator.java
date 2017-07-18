package model.bookings;

import model.accounts.User;
import model.campus.CampusSearchQuery;
import model.campus.CampusSearchQueryGenerator;
import model.rooms.Room;
import model.subjects.CampusSubject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * CampusSearchQueryGenerator implementation for Booking
 */
public class BookingSearchQueryGenerator implements CampusSearchQueryGenerator<Booking> {

    private Integer bookingID;
    private User booker;
    private Room room;
    private CampusSubject subject;
    private Booking.WeekDay day;
    private Time startTime;
    private Time endTime;
    private Date bookingDate;
    private String description;
    private Date dateFrom;
    private Date dateTo;

    /**
     * constructor of BookingSearchQueryGenerator class
     *
     * @param bookingID   booking id in database
     * @param booker      booker (User object)
     * @param room        booked room (Room object)
     * @param subject     booking subject (CampusSubject object)
     * @param day         weekday of booking (WeekDay enum type)
     * @param startTime   booking start time
     * @param endTime     booking end time
     * @param description booking description
     * @param bookingDate booking date
     * @param dateFrom    date from where booking is being searched
     * @param dateTo      date to when booking is being searched
     */
    public BookingSearchQueryGenerator(Integer bookingID, User booker, Room room, CampusSubject subject, Booking.WeekDay day,
                                       Time startTime, Time endTime, String description, Date bookingDate,
                                       Date dateFrom, Date dateTo) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.room = room;
        this.subject = subject;
        this.day = day;
        this.startTime = startTime == null ? null : new Time(startTime.getTime());
        this.endTime = endTime == null ? null : new Time(endTime.getTime());
        this.description = description;
        this.bookingDate = bookingDate == null ? null : new Date(bookingDate.getTime());
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    /**
     * Default Constructor
     */
    public BookingSearchQueryGenerator() {
        this(null, null, null, null, null,
                null, null, null, null, null, null);
    }

    /**
     * @return date from booking is being searched
     */
    public Date getDateFrom() {
        return new Date(dateFrom.getTime());
    }

    /**
     * sets date from where booking is being searched
     */
    public void setDateFrom(Date from) {
        this.dateFrom = from == null ? null : new Date(from.getTime());
    }

    /**
     * @return date to when booking is being searched
     */
    public Date getDateTo() {
        return new Date(dateTo.getTime());
    }

    /**
     * sets date to when booking is being searched
     */
    public void setDateTo(Date to) {
        this.dateTo = to == null ? null : new Date(to.getTime());
    }

    /**
     * @return booking date (specific, not when searching for whole range)
     */
    public Date getBookingDate() {
        return bookingDate == null ? null : new Date(bookingDate.getTime());
    }

    /**
     * @return date to when booking is being searched
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate == null ? null : new Date(bookingDate.getTime());
    }

    /**
     * @return id of booking being searched for in database
     */
    public int getId() {
        return bookingID;
    }

    /**
     * sets id of booking being searched for in database
     */
    public void setId(int bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * @return user that made the booking being searched for
     */
    public User getBooker() {
        return booker;
    }

    /**
     * sets user that made the booking being searched for
     */
    public void setBooker(User booker) {
        this.booker = booker;
    }

    /**
     * @return room where the booking being searched for was made
     */
    public Room getRoom() {
        return room;
    }

    /**
     * sets room where the booking being searched for was made
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return subject for which the booking being searched for was made
     */
    public CampusSubject getSubject() {
        return subject;
    }

    /**
     * sets subject for which the booking being searched for was made
     */
    public void setSubject(CampusSubject subject) {
        this.subject = subject;
    }

    /**
     * @return end time of range in which the booking is being searched
     */
    public Time getEndTime() {
        return new Time(endTime.getTime());
    }

    /**
     * sets end time of range in which the booking is being searched
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * @return start time of range in which the booking is being searched
     */
    public Time getStartTime() {
        return new Time(startTime.getTime());
    }

    /**
     * sets start time of range in which the booking is being searched
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * @return WeekDay on which the booking is being searched
     */
    public Booking.WeekDay getDay() {
        return day;
    }

    /**
     * sets WeekDay on which the booking is being searched
     */
    public void setDay(Booking.WeekDay day) {
        this.day = day;
    }

    /**
     * @return description of the booking being searched for
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description of the booking being searched for
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public CampusSearchQuery generateQuery() {
        List<Object> values = new ArrayList<>();
        String sql = hasNonNullFields() ? String.format("SELECT * FROM \n%s \nJOIN %s ON %s.%s = %s.%s\n" +
                        " JOIN %s ON %s.%s = %s.%s \n" +
                        joinSubject() +
                        " \nWHERE \n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
                SQL_TABLE_BOOKING, SQL_TABLE_ROOM, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_USER, SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_BOOKER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID,
                generateEqualQuery(bookingID, SQL_COLUMN_BOOKING_ID, values) + " AND ",
                generateEqualQuery(booker, SQL_COLUMN_BOOKING_BOOKER, values) + " AND ",
                generateEqualQuery(room == null ? null : room.getId(),
                        SQL_TABLE_ROOM + "." + SQL_COLUMN_BOOKING_ROOM, values) + " AND ",
                generateLikeQuery(subject, SQL_COLUMN_BOOKING_SUBJECT_ID, values) + " AND ",
                generateTimeQuery(endTime, SQL_COLUMN_BOOKING_START_TIME, values, false) + " AND ",
                generateTimeQuery(startTime, SQL_COLUMN_BOOKING_END_TIME, values, true) + " AND ",
                generateDateQuery(dateFrom, SQL_COLUMN_BOOKING_BOOKING_DATE, values, true) + " AND ",
                generateDateQuery(dateTo, SQL_COLUMN_BOOKING_BOOKING_DATE, values, false) + " AND ",
                generateLikeQuery(description, SQL_COLUMN_BOOKING_DESCRIPTION, values) + " AND ",
                generateWeekDayQuery(day, SQL_COLUMN_BOOKING_BOOKING_DATE, values) + " AND ",
                generateDateEqualsQuery(bookingDate, SQL_COLUMN_BOOKING_BOOKING_DATE, values)
        ) : "SELECT * FROM " + SQL_TABLE_BOOKING;
        return new CampusSearchQuery(sql, asArray(values));
    }

    /**
     * generates query for finding bookings on given WeekDay
     *
     * @param day        WeekDay on which found bookings should take place
     * @param columnName column name of WeekDay in booking table in database
     * @param values     this day in correct format will be added to this array of objects (for PreparedStatement)
     * @return
     */
    private String generateWeekDayQuery(Booking.WeekDay day, String columnName, List<Object> values) {
        if (day == null) return " 1 = 1 ";
        values.add(day.ordinal());
        return " WEEKDAY(" + columnName + ") = ?";
    }

    /**
     * @return query for joining subject table to other tables
     */
    private String joinSubject() {
        return String.format(" LEFT JOIN %s ON %s.%s = %s.%s", SQL_TABLE_SUBJECT, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_TABLE_SUBJECT, SQL_COLUMN_SUBJECT_ID);
    }

    /**
     * checks if given search query has non null fields
     * so that there will be something to search for
     *
     * @return true if there is at least one non null field
     */
    private boolean hasNonNullFields() {
        return bookingID != null || booker != null
                || room != null || subject != null
                || day != null || startTime != null
                || endTime != null || bookingDate != null
                || description != null || dateTo != null || dateFrom != null;
    }
}
