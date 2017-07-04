package serve.managers;

import model.bookings.Booking;
import model.bookings.BookingManager;
import model.bookings.BookingSearchQueryGenerator;
import model.campus.CampusSearchQuery;
import model.lectures.CampusSubject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static misc.Utils.getBookingFromResults;
import static misc.Utils.successfulOperation;
import static model.database.SQLConstants.*;

public class DefaultBookingManager implements BookingManager {

    private static BookingManager instance;

    private final DBConnector connector;

    DefaultBookingManager(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public List<Booking> find(BookingSearchQueryGenerator queryGenerator) {
        List<Booking> list = new ArrayList<>();
        CampusSearchQuery query = queryGenerator.generateQuery();
        try (ResultSet set = connector.executeQuery(query.getQuery(), query.getValues())) {


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
        String date = format.format(booking.getStartDate());

        String description = booking.getDescription();
        CampusSubject subject = booking.getSubject();

        String sql = String.format("INSERT  INTO  booking " +
                        "(%s, %s, %s, %s, %s, %s, %s, %s, %s)" +
                        "  VALUE "
                        + "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                SQL_COLUMN_BOOKING_ROOM, SQL_COLUMN_BOOKING_BOOKER, SQL_COLUMN_BOOKING_START_DATE,
                SQL_COLUMN_BOOKING_END_DATE, SQL_COLUMN_BOOKING_SUBJECT_ID, SQL_COLUMN_BOOKING_DESCRIPTION,
                SQL_COLUMN_BOOKING_START_TIME, SQL_COLUMN_BOOKING_END_TIME, SQL_COLUMN_BOOKING_WEEK_DAY
        );

        return successfulOperation(sql, connector, booking.getRoom().getId(), booking.getBooker().getId(),
                booking.getStartDate(), booking.getEndDate(), (subject == null ? null : subject.getId()),
                description, booking.getStartTime(),
                booking.getEndTime(), booking.getDay().name().toLowerCase());
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

        Booking thisBooking = find(bookingQueryGenerator).get(0);

        String deleteQuery = "delete from " + SQL_TABLE_BOOKING + " where " + SQL_COLUMN_BOOKING_ROOM + "=? AND " +
                SQL_COLUMN_BOOKING_BOOKER + "=? AND " + SQL_COLUMN_BOOKING_SUBJECT_ID + "=? AND \n"
                + SQL_COLUMN_BOOKING_START_TIME + "=? AND " + SQL_COLUMN_BOOKING_END_TIME + "=? AND " + SQL_COLUMN_BOOKING_WEEK_DAY + "=?;";

        try {
            connector.executeUpdate(deleteQuery, thisBooking.getRoom().getId(), thisBooking.getBooker().getId(),
                    thisBooking.getSubject().getId(), thisBooking.getStartTime(), thisBooking.getEndTime(),
                    thisBooking.getDay().name().toLowerCase());
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


}
