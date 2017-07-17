package serve.managers;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.campus.CampusSearchQuery;
import model.rooms.Room;
import model.subjects.CampusSubject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static misc.Utils.*;
import static model.database.SQLConstants.*;

/**
 * implementation of BookingManager (DAO for bookings)
 */
public class DefaultBookingManager implements BookingManager {

    private static BookingManager instance;

    private final DBConnector connector;

    /**
     * constructor of DefaultBookingManager class
     *
     * @param connector DBConnector object, so that this class can interact with database
     */
    DefaultBookingManager(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public List<Booking> find(BookingSearchQueryGenerator queryGenerator) {
        List<Booking> list = new ArrayList<>();
        CampusSearchQuery query = queryGenerator.generateQuery();
        String sql = query.getQuery();
        Object[] values = query.getValues();
        try (ResultSet set = connector.executeQuery(sql, values)) {


            while (set.next()) {
                list.add(getBookingFromResults(set));
            }

        } catch (SQLException e) {
            e.printStackTrace();

            //doing nothing
        }
        return list;
    }

    @Override
    public boolean add(Booking booking) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(booking.getBookingDate());
        String description = booking.getDescription();
        CampusSubject subject = booking.getSubject();

        String sql = String.format("INSERT  INTO  booking " +
                        "(%s, %s, %s, %s, %s, %s, %s)" +
                        "  VALUE "
                        + "(?, ?, ?, ?, ?, ?, ?)",
                SQL_COLUMN_BOOKING_ROOM, SQL_COLUMN_BOOKING_BOOKER, SQL_COLUMN_BOOKING_BOOKING_DATE,
                SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_COLUMN_BOOKING_DESCRIPTION,
                SQL_COLUMN_BOOKING_START_TIME, SQL_COLUMN_BOOKING_END_TIME
        );

        return successfulOperation(sql, connector, booking.getRoom().getId(), booking.getBooker().getId(),
                booking.getBookingDate(), (subject == null ? null : subject.getId()),
                description, booking.getStartTime(),
                booking.getEndTime());
    }

    @Override
    public boolean remove(int entityId) {
        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where "
                + SQL_COLUMN_BOOKING_ID + " = ?";
        return successfulOperation(deleteQuery, connector, entityId);
    }

    @Override
    public boolean deleteAllOccurrences(int bookingId) {
        BookingSearchQueryGenerator bookingQueryGenerator = new BookingSearchQueryGenerator();
        bookingQueryGenerator.setId(bookingId);

        Booking booking = find(bookingQueryGenerator).get(0);
        int weekDay = getWeekDay(booking.getBookingDate());
        String sql = String.format("DELETE FROM %s " +
                        "WHERE %s = ? \n" +
                        "AND %s = ? \n" +
                        "AND %s = ? \n" +
                        "AND %s = ? \n" +
                        "AND %s = ?" +
                        "AND WEEKDAY(%s) = WEEKDAY(?)",
                SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_ROOM, SQL_COLUMN_BOOKING_BOOKER,
                SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_COLUMN_BOOKING_START_TIME, SQL_COLUMN_BOOKING_END_TIME,
                SQL_COLUMN_BOOKING_BOOKING_DATE);


        try {
            connector.executeUpdate(sql, booking.getRoom().getId(), booking.getBooker().getId(),
                    booking.getSubject().getId(), booking.getStartTime(), booking.getEndTime(),
                    booking.getBookingDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void removeAllBookings() {
        String truncateQuery = "truncate table " + SQL_TABLE_BOOKING;
        try {
            connector.executeUpdate(truncateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllLectures() {
        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where " +
                SQL_COLUMN_BOOKING_SUBJECT_ID + " > 0";
        try {
            connector.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> getAllBookingsOn(Date day, Room room) {
        List<Booking> bookings = new ArrayList<>();
        String sql = String.format("SELECT * FROM " +
                        "%s \n" +
                        " JOIN %s ON %s.%s = %s.%s \n" +
                        "LEFT JOIN %s ON %s.%s = %s.%s\n" +
                        "JOIN %s ON %s.%s = %s.%s\n" +
                        "WHERE \n" +
                        "%s.%s = ?\n" +
                        "AND %s = STR_TO_DATE(?, %s)",
                SQL_TABLE_BOOKING, SQL_TABLE_ROOM, SQL_TABLE_BOOKING,
                SQL_COLUMN_BOOKING_ROOM, SQL_TABLE_ROOM, SQL_COLUMN_ROOM_ID,
                SQL_TABLE_SUBJECT, SQL_TABLE_BOOKING, SQL_COLUMN_SUBJECT_ID,
                SQL_TABLE_SUBJECT, SQL_COLUMN_SUBJECT_ID,
                SQL_TABLE_USER, SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_BOOKER,
                SQL_TABLE_USER, SQL_COLUMN_USER_ID, SQL_TABLE_BOOKING, SQL_COLUMN_BOOKING_ROOM,
                SQL_COLUMN_BOOKING_BOOKING_DATE, "'%d.%m.%Y'");
        String d = dateToString(day, "dd.MM.yy") + "";
        try (ResultSet results = connector.executeQuery(sql, room.getId(), d)) {
            while (results.next()) {
                bookings.add(getBookingFromResults(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //ignored. returning empty list.
        }
        return bookings;
    }


}
